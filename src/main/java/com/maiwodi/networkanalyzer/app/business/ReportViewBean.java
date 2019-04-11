package com.maiwodi.networkanalyzer.app.business;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maiwodi.networkanalyzer.app.backend.NetworkData;

@Named("reportViewBean")
@ViewScoped
public class ReportViewBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5920874013441954154L;

	private LineChartModel lineModel;

	// TODO: remove it later
	private String json = "[ {\r\n" + "\r\n" + "        \"rssiValue\" : -55,\r\n" + "        \"speedInMbps\" : 72,\r\n"
			+ "        \"timeStamp\" : \"1554603519\"\r\n" + "      }, {\r\n" + "        \"rssiValue\" : -55,\r\n"
			+ "        \"speedInMbps\" : 72,\r\n" + "        \"timeStamp\" : \"1554603521\"\r\n" + "      }, {\r\n"
			+ "        \"rssiValue\" : -55,\r\n" + "        \"speedInMbps\" : 72,\r\n"
			+ "        \"timeStamp\" : \"1554603523\"\r\n" + "      }, {\r\n" + "        \"rssiValue\" : -53,\r\n"
			+ "        \"speedInMbps\" : 72,\r\n" + "        \"timeStamp\" : \"1554603525\"\r\n" + "      }, {\r\n"
			+ "        \"rssiValue\" : -57,\r\n" + "        \"speedInMbps\" : 72,\r\n"
			+ "        \"timeStamp\" : \"1554603527\"\r\n" + "      }, {\r\n" + "        \"rssiValue\" : -57,\r\n"
			+ "        \"speedInMbps\" : 72,\r\n" + "        \"timeStamp\" : \"1554603529\"\r\n" + "      }, {\r\n"
			+ "        \"rssiValue\" : -54,\r\n" + "        \"speedInMbps\" : 72,\r\n"
			+ "        \"timeStamp\" : \"1554603531\"\r\n" + "      }, {\r\n" + "        \"rssiValue\" : -54,\r\n"
			+ "        \"speedInMbps\" : 72,\r\n" + "        \"timeStamp\" : \"1554603533\"\r\n" + "      }, {\r\n"
			+ "        \"rssiValue\" : -54,\r\n" + "        \"speedInMbps\" : 72,\r\n"
			+ "        \"timeStamp\" : \"1554603535\"\r\n" + "      }, {\r\n" + "        \"rssiValue\" : -55,\r\n"
			+ "        \"speedInMbps\" : 72,\r\n" + "        \"timeStamp\" : \"1554603537\"\r\n" + "      }, {\r\n"
			+ "        \"rssiValue\" : -55,\r\n" + "        \"speedInMbps\" : 72,\r\n"
			+ "        \"timeStamp\" : \"1554603539\"\r\n" + "      }, {\r\n" + "        \"rssiValue\" : -55,\r\n"
			+ "        \"speedInMbps\" : 72,\r\n" + "        \"timeStamp\" : \"1554603541\"\r\n" + "      }, {\r\n"
			+ "        \"rssiValue\" : -54,\r\n" + "        \"speedInMbps\" : 72,\r\n"
			+ "        \"timeStamp\" : \"1554603543\"\r\n" + "      }, {\r\n" + "        \"rssiValue\" : -55,\r\n"
			+ "        \"speedInMbps\" : 72,\r\n" + "        \"timeStamp\" : \"1554603545\"\r\n" + "      } ]";

	@PostConstruct
	public void init() {
		createLineModel();
	}

	// TODO: Test. remove it later
	public void testButton() {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			NetworkData[] networkDatas = objectMapper.readValue(this.getJson(), NetworkData[].class);

//			System.out.println(networkDatas);

			createLineModel(networkDatas);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param networkDatas
	 */
	public void createLineModel(NetworkData[] networkDatas) {
		lineModel = new LineChartModel();
		ChartData data = new ChartData();

		LineChartDataSet dataSet = new LineChartDataSet();
		List<Number> values = new ArrayList<>();

		for (int i = 0; i < networkDatas.length; i++) {
			NetworkData netData = networkDatas[i];
			values.add(netData.getSpeedInMbps());
		}

		dataSet.setData(values);
		dataSet.setFill(false);
		dataSet.setLabel("Network Data  Dataset");
		dataSet.setBorderColor("rgb(75, 192, 192)");
		dataSet.setLineTension(0.1);
		data.addChartDataSet(dataSet);

		List<String> labels = new ArrayList<>();

		for (int i = 0; i < networkDatas.length; i++) {
			labels.add("" + i);
		}
		data.setLabels(labels);

		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Network Analysis Chart");
		options.setTitle(title);

		lineModel.setOptions(options);
		lineModel.setData(data);

	}

	public void createLineModel() {
		lineModel = new LineChartModel();
		ChartData data = new ChartData();

		LineChartDataSet dataSet = new LineChartDataSet();
		List<Number> values = new ArrayList<>();
		values.add(65);
		values.add(59);
		values.add(80);
		values.add(81);
		values.add(56);
		values.add(55);
		values.add(40);
		dataSet.setData(values);
		dataSet.setFill(false);
		dataSet.setLabel("My First Dataset");
		dataSet.setBorderColor("rgb(75, 192, 192)");
		dataSet.setLineTension(0.1);
		data.addChartDataSet(dataSet);

		List<String> labels = new ArrayList<>();
		labels.add("January");
		labels.add("February");
		labels.add("March");
		labels.add("April");
		labels.add("May");
		labels.add("June");
		labels.add("July");
		data.setLabels(labels);

		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Network Analysis Chart");
		options.setTitle(title);

		lineModel.setOptions(options);
		lineModel.setData(data);
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
				"Item Index: " + event.getItemIndex() + ", DataSet Index:" + event.getDataSetIndex());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}
