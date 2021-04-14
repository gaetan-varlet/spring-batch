package com.formation.exemple.file;

import com.formation.exemple.model.InputProduct;
import com.formation.exemple.model.OutputProduct;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductProcessor implements ItemProcessor<InputProduct, OutputProduct> {

	@Override
	public OutputProduct process(InputProduct item) throws Exception {
		if (item.getFournisseurId() == 1) {
			return new OutputProduct(item);
		}
		return null;
	}

}
