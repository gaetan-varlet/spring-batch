package com.formation.exemple;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchApplication implements CommandLineRunner {

	@Autowired
	private Job productJob;
	@Autowired
	private JobLauncher jobLauncher;
	@Value("${appli.id:2}")
	private long id;

	public static void main(String[] args) {
		System.out.println("DEBUT RUN DANS LE MAIN");
		SpringApplication.run(SpringBatchApplication.class, args);
		System.out.println("APRES RUN DANS LE MAIN");
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("DEBUT RUN DANS COMMAND LINE RUNNER");
		Map<String, JobParameter> parametersMap = new HashMap<>();
		parametersMap.put("ID", new JobParameter(id));
		parametersMap.put("DATE", new JobParameter(new Date(), false));
		JobParameters jobParameters = new JobParameters(parametersMap);
		JobExecution jobExecution = jobLauncher.run(productJob, jobParameters);
		System.out.println("JOB EXECUTION : " + jobExecution);
		System.out.println("FIN RUN DANS COMMAND LINE RUNNER");
	}

}
