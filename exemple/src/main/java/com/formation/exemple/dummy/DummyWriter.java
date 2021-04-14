package com.formation.exemple.dummy;

import java.util.List;

import com.formation.exemple.model.DummyRecord;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class DummyWriter implements ItemWriter<DummyRecord> {

	@Override
	public void write(List<? extends DummyRecord> items) throws Exception {
		System.out.println(items);
	}

}
