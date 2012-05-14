package services;

import java.util.Date;
import java.util.List;

import org.sonar.wsclient.Host;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.*;
import org.sonar.wsclient.Sonar;

public class SonarService {
	
	public static List<Measure> testSonarAPI()
    {
        Sonar sonar = new Sonar(new HttpClient4Connector(new Host(ServicesConfiguration.getSonarUrl(), ServicesConfiguration.getSonarUser(), ServicesConfiguration.getSonarPassword())));              
        Resource resource = sonar.find(
        		ResourceQuery.createForMetrics(
        				"nl.bibliotheek.esb:rotterdam-adapter", // resourceKey or resourceId
        				
        				//MetricKeys
        				"blocker_violations",
        				"critical_violations",
        				"major_violations",
        				"minor_violations",
        				"info_violations",
        				"violations",
        				"coverage",
        				"ncloc",
        				"qi-quality-index",
        				"test_success_density"
        		)
        );
               
        return resource.getMeasures();    
    }

}
