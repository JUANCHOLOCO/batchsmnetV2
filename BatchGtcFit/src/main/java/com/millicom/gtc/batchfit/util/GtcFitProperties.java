package com.millicom.gtc.batchfit.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gtcfit")
public class GtcFitProperties {

	private String url;
    private String urlaction;

    // Getters y setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlaction() {
        return urlaction;
    }

    public void setUrlaction(String urlaction) {
        this.urlaction = urlaction;
    }
}
