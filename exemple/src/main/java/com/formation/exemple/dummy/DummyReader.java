package com.formation.exemple.dummy;

import com.formation.exemple.model.DummyRecord;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class DummyReader implements ItemReader<DummyRecord> {

	private int i = 0;
	private int nbIterations = 10;

	// public DummyReader(@Value("#{jobParameters[input]}") String nbIterations) {
	// this.nbIterations = Integer.parseInt(nbIterations);
	// }

	@Override
	public DummyRecord read() throws Exception {
		if (i < nbIterations) {
			i++;
			return new DummyRecord();
		}
		return null;
	}

}
