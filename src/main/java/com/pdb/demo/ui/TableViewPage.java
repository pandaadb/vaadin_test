package com.pdb.demo.ui;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.pdb.demo.model.RepairRequest;
import com.pdb.demo.repo.RepairRequestDao;
import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.server.SerializableSupplier;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FetchItemsCallback;
import com.vaadin.ui.UI;

@SpringUI(path = "/overview")
@Theme("valo")
public class TableViewPage extends UI {
	private static final long serialVersionUID = 1L;

	@Autowired
	RepairRequestDao dao;

	private Grid<RepairRequest> requestGrid;

	@Override
	protected void init(VaadinRequest request) {
		requestGrid = new Grid<>(RepairRequest.class);
		requestGrid.setSizeFull();
		setContent(requestGrid);
		requestGrid.setDataProvider(new FetchItemsCallback<RepairRequest>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Stream<RepairRequest> fetchItems(List<QuerySortOrder> sortOrder, int offset, int limit) {
				return StreamSupport.stream(dao.findAll().spliterator(), false);
			}
		}, new SerializableSupplier<Integer>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Integer get() {
				return (int) dao.count();
			}
		});
	}

	@Scheduled(fixedDelay = 5000)
	void refreshGrid() {
		if (requestGrid == null) {
			return;
		}
		System.out.println("redone");
		requestGrid.getDataProvider().refreshAll();
		requestGrid.clearSortOrder();
	}

}
