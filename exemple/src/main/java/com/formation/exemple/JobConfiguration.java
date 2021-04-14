package com.formation.exemple;

import com.formation.exemple.dummy.DummyReader;
import com.formation.exemple.dummy.DummyWriter;
import com.formation.exemple.model.DummyRecord;
import com.formation.exemple.model.InputProduct;
import com.formation.exemple.model.OutputProduct;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DummyReader dummyReader;
	@Autowired
	private DummyWriter dummyWriter;

	@Autowired
	private ItemReader<InputProduct> productReader;
	@Autowired
	private ItemReader<InputProduct> jsonProductReader;
	@Autowired
	private ItemReader<InputProduct> bddProductReader;
	@Autowired
	private ItemWriter<OutputProduct> productWriter;
	@Autowired
	private ItemWriter<OutputProduct> xmlProductWriter;
	@Autowired
	private CompositeItemProcessor<InputProduct, OutputProduct> compositeItemProcessor;

	@Bean
	public Job dummyJob() {
		return this.jobBuilderFactory.get("dummyJob") // nom qu'on donne à notre job
				.start(firstStep()) // job qui commence par cette étape
				.next(secondStep()) // puis qui continue par cette étape
				.listener(this) // pour pouvoir écouter ce que fait Spring Batch
				.build();
	}

	@Bean
	public Step firstStep() {
		return this.stepBuilderFactory.get("step1") // nom de notre étape
				.<DummyRecord, DummyRecord>chunk(2) // chunk = morceau // traitement par paquet de n
				.reader(dummyReader) // définition du reader
				.writer(dummyWriter) // définition du writer
				.build();
	}

	@Bean
	public Step secondStep() {
		return this.stepBuilderFactory.get("step2").<DummyRecord, DummyRecord>chunk(3).reader(dummyReader)
				.writer(dummyWriter).build();
	}

	@Bean
	public Job productJob() {
		return this.jobBuilderFactory.get("productJob") // nom qu'on donne à notre job
				.start(productStep()) // job qui commence par cette étape
				.next(productStep2()) // puis qui continue par cette étape
				.listener(this) // pour pouvoir écouter ce que fait Spring Batch
				.build();
	}

	@Bean
	public Step productStep() {
		return this.stepBuilderFactory.get("step Product") // nom de notre étape
				.<InputProduct, OutputProduct>chunk(50) // chunk = morceau // traitement par paquet de n
				// .reader(productReader) // définition du reader
				.reader(jsonProductReader) // définition du reader
				// .reader(bddProductReader)// définition du reader
				.processor(compositeItemProcessor) // définition du processor
				// .writer(productWriter) // définition du writer
				.writer(xmlProductWriter) // définition du writer
				.faultTolerant()// autorise des erreurs
				.skipLimit(200) // nombre max d'erreurs autorisé
				.skip(ValidationException.class) // exception renvoyée si dépassement de la limite
				.build();
	}

	@Bean
	public Step productStep2() {
		return this.stepBuilderFactory.get("step Product 2") // nom de notre étape
				.<InputProduct, OutputProduct>chunk(50) // chunk = morceau // traitement par paquet de n
				// .reader(productReader) // définition du reader
				.reader(jsonProductReader) // définition du reader
				// .reader(bddProductReader)// définition du reader
				.processor(compositeItemProcessor) // définition du processor
				// .writer(productWriter) // définition du writer
				.writer(xmlProductWriter) // définition du writer
				.faultTolerant().skipLimit(200).skip(ValidationException.class) //
				.allowStartIfComplete(true) // permet de relancer la step même si elle a déjà été complété auparavant
				.build();
	}

	@Bean
	public Step productStep3() {
		return this.stepBuilderFactory.get("step Product 3") // nom de notre étape
				.<InputProduct, OutputProduct>chunk(50) // chunk = morceau // traitement par paquet de n
				// .reader(productReader) // définition du reader
				.reader(jsonProductReader) // définition du reader
				// .reader(bddProductReader)// définition du reader
				.processor(compositeItemProcessor) // définition du processor
				// .writer(productWriter) // définition du writer
				.writer(xmlProductWriter) // définition du writer
				.startLimit(2) // nombre de fois où on a le droit d'exécuter la step
				.build();
	}

	@BeforeJob
	void beforeJob(JobExecution jobExecution) {
		System.out.println("BEFORE JOB");
		System.out.println(jobExecution);
	}

	@AfterJob
	void afterJob(JobExecution jobExecution) {
		System.out.println("AFTER JOB");
		System.out.println(jobExecution);
	}

}
