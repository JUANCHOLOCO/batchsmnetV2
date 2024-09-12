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
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.support.JdbcTransactionManager;

import com.millicom.gtc.batchfit.entity.TestPlan;
import com.millicom.gtc.batchfit.processor.GtcDataProcessor;
import com.millicom.gtc.batchfit.database.TestPlanPreparedStatementSetter;
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
		return new JobBuilder("Job", jobRepository)
				.incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
				.build();
	}

	
	@Bean
	public Step step1(JobRepository jobRepository, JdbcTransactionManager transactionManager,
	                           ItemReader<TestPlan> gtcDataTableReader,
	                           ItemProcessor<TestPlan, String> gtcDataProcessor,
	                           ItemWriter<String> gtcDataTableWriter) {
	    
	    return new StepBuilder("gtcGeneration", jobRepository)
	            .<TestPlan, String>chunk(10, transactionManager)
	            .reader(new ItemReader<TestPlan>() {
	                private final ItemReader<TestPlan> delegate = gtcDataTableReader;
	                private boolean noData = false;

	                @Override
	                public TestPlan read() throws Exception {
	                    if (noData) {
	                        return null;  
	                    }
	                    TestPlan item = delegate.read();
	                    if (item == null) {
	                        System.out.println("No se encontraron datos para procesar en la consulta.");
	                        noData = true;  
	                        return null;  
	                    }
	                    return item;  
	                }
	            })
	            .processor(gtcDataProcessor)
	            .writer(gtcDataTableWriter)
	            .listener(new StepExecutionListener() {
	                @Override
	                public void beforeStep(StepExecution stepExecution) {
	                    
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
	public JdbcPagingItemReader<TestPlan> gtcDataTableReader(DataSource dataSource) {
	    String sql = "select * from test_plans where status = 'IN_PROGRESS'";
	    System.out.println("gtcDataTableReader Juan" + sql);
	    System.out.println("gtcDataTableReader JuanUrl" + url);
	    JdbcPagingItemReader<TestPlan> reader = new JdbcPagingItemReader<TestPlan>();
	    reader.setDataSource(dataSource);
	    reader.setPageSize(1000);
	    reader.setRowMapper(new DataClassRowMapper<>(TestPlan.class));

	    MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
	    queryProvider.setSelectClause("SELECT *");
	    queryProvider.setFromClause("FROM test_plans");
	    queryProvider.setWhereClause("WHERE status = 'IN_PROGRESS'");
	    queryProvider.setSortKeys(Collections.singletonMap("diagnostic_id", Order.ASCENDING));

	    reader.setQueryProvider(queryProvider);

	
	    reader.setSaveState(true);  
	    return reader;
	}

	
	@Bean
	public JdbcBatchItemWriter<String> gtcDataTableWriter(DataSource dataSource) {
		System.out.println("gtcDataTableWriter Juan");
		
		
		String sql = "UPDATE test_plans SET status = 'COMPLETED' WHERE diagnostic_id = ? ";
		return new JdbcBatchItemWriterBuilder<String>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .itemPreparedStatementSetter(new TestPlanPreparedStatementSetter())
	            .build();
	}
	
	@Bean
	public GtcDataProcessor gtcDataProcessor() {
	    return new GtcDataProcessor();
	}


}
