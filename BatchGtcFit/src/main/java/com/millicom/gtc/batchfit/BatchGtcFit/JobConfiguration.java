package com.millicom.gtc.batchfit.BatchGtcFit;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.support.JdbcTransactionManager;
import com.millicom.gtc.batchfit.entity.TestPlan;
import com.millicom.gtc.batchfit.processor.GtcDataProcessor;
import com.millicom.gtc.batchfit.dto.smnet.TestPlanUpdateDto;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.ExitStatus;

@Configuration
public class JobConfiguration {

	@Value("${gtcfit.url}")
	private String url;

	private final DataSource dataSource;

	public JobConfiguration(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public JdbcTransactionManager transactionManager() {
		return new JdbcTransactionManager(dataSource);
	}

	/*
	 * @Bean public Job job(JobRepository jobRepository, Step step1) { return new
	 * JobBuilder("Job", jobRepository) .start(step1) .build(); }
	 */

	@Bean
	public Job job(JobRepository jobRepository, Step step1) {
		return new JobBuilder("Job", jobRepository).incrementer(new RunIdIncrementer()).flow(step1).end().build();
	}
	@Bean
	public Step step1(JobRepository jobRepository, JdbcTransactionManager transactionManager,
	        ItemReader<TestPlan> gtcDataTableReader, ItemProcessor<TestPlan, TestPlanUpdateDto> gtcDataProcessor,
	        ItemWriter<TestPlanUpdateDto> gtcDataTableWriter) {

	    return new StepBuilder("gtcGeneration", jobRepository)
	            .<TestPlan, TestPlanUpdateDto>chunk(10, transactionManager)
	            .reader(gtcDataTableReader)
	            .processor(gtcDataProcessor)
	            .writer(gtcDataTableWriter)
	            .listener(new StepExecutionListener() {
	                @Override
	                public void beforeStep(StepExecution stepExecution) {
	                    System.out.println("Iniciando Step...");
	                }

	                @Override
	                public ExitStatus afterStep(StepExecution stepExecution) {
	                    if (stepExecution.getReadCount() == 0) {
	                        System.out.println("No se procesaron datos. El Step finaliza sin ejecutar el procesamiento.");
	                    }
	                    return stepExecution.getExitStatus();
	                }
	            })
	            .build();
	}

	@Bean
	public JdbcCursorItemReader<TestPlan> gtcDataTableReader(DataSource dataSource) {
	    String sql = "SELECT * FROM test_plans WHERE status = 'IN_PROGRESS' ORDER BY diagnostic_id ASC";
	    
	    JdbcCursorItemReader<TestPlan> reader = new JdbcCursorItemReader<>();
	    reader.setDataSource(dataSource);
	    reader.setSql(sql);
	    reader.setRowMapper(new DataClassRowMapper<>(TestPlan.class));
	    
	    return reader;
	}

	@Bean
	public JdbcBatchItemWriter<TestPlanUpdateDto> gtcDataTableWriter(DataSource dataSource) {
	    String sql = "UPDATE test_plans SET status = ? WHERE diagnostic_id = ?";

	    return new JdbcBatchItemWriterBuilder<TestPlanUpdateDto>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .itemPreparedStatementSetter((item, ps) -> {
	                ps.setString(1, item.getNewStatus());
	                ps.setString(2, item.getDiagnosticId());
	            })
	            .build();
	}

	@Bean
	public GtcDataProcessor gtcDataProcessor() {
		return new GtcDataProcessor();
	}

}
