package com.pdb.demo.ui;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pdb.demo.model.RepairRequest;
import com.pdb.demo.repo.RepairRequestDao;
import com.vaadin.annotations.Theme;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@SpringUI
@Theme("valo")
public class FormSubmit extends UI {
	private static final long serialVersionUID = 1L;

	@Autowired
	RepairRequestDao dao;
	
	@Override
	protected void init(VaadinRequest request) {
		
		TextField name = create("Name");
		TextField email = create("Email Address");
		TextField phoneNumber = create("Phone number");
		
		DateField availableAt = new DateField(" Available on");
		availableAt.setWidth(50, Unit.PERCENTAGE);
		
		ComboBox<String> problem = createCombo("The issue", Arrays.asList("Water damage", "Heater broken", "Electricity broken", "Kitchen utilities"));
		
		TextArea description = createArea("Description");
		
		final BeanValidationBinder<RepairRequest> requestBinder = new BeanValidationBinder<>(RepairRequest.class);
		requestBinder.bind(name, "name");
		requestBinder.bind(email, "email");
		requestBinder.bind(phoneNumber, "phoneNumber");
		requestBinder.bind(problem, "issue");
		requestBinder.bind(description, "description");
		requestBinder.bind(availableAt, "availability");
		
		FormLayout layout = new FormLayout();
		layout.setWidth(100, Unit.PERCENTAGE);
		
		layout.addComponent(name);
		layout.addComponent(email);
		layout.addComponent(phoneNumber);
		layout.addComponent(availableAt);
		layout.addComponent(problem);
		layout.addComponent(description);
		
		Label statusLabel = new Label("message");
		statusLabel.setVisible(false);
		requestBinder.setStatusLabel(statusLabel);
		
		layout.addComponent(statusLabel);
		
		
		Button submit = new Button("Submit", event -> {
			
			BinderValidationStatus<RepairRequest> validate = requestBinder.validate();
			if(!validate.isOk()) {
				return;
			}
			RepairRequest bean = new RepairRequest();
			try {
				requestBinder.writeBean(bean);
			} catch (ValidationException e) {
				e.printStackTrace();
			}
			dao.save(bean);
		});
		
		layout.addComponent(submit);
		
		setContent(layout);
		
		
		
	}
	
	TextField create(final String label) {
		TextField t = new TextField(label);
		t.setWidth(50, Unit.PERCENTAGE);
		return t;
	}
	
	TextArea createArea(final String label) {
		TextArea t = new TextArea(label);
		t.setWidth(50, Unit.PERCENTAGE);
		return t;
	}
	
	ComboBox<String> createCombo(final String label, List<String> options) {
		ComboBox<String> t = new ComboBox<>(label, options);
		t.setWidth(50, Unit.PERCENTAGE);
		return t;
	}
	
	
}
