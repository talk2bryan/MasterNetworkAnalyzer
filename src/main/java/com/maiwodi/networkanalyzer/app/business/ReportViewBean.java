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
import com.maiwodi.networkanalyzer.app.backend.models.NetworkData;
import com.maiwodi.networkanalyzer.app.backend.models.NetworkDataSummary;
import com.maiwodi.networkanalyzer.app.models.Workers;
import com.maiwodi.networkanalyzer.app.utils.JerseyClient;
import com.maiwodi.networkanalyzer.app.utils.Utilities;

@Named("reportViewBean")
@ViewScoped
public class ReportViewBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5920874013441954154L;

	private LineChartModel lineModel;

	private NetworkDataSummary networkDataSummary;

	private String json = "[ {\r\n" + "      \"downloadSpeed\" : 0.4800998608,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4869734599,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4830217843,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4914971002,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4932669067,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4870446133,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4770537162,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.5039306591,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4916179146,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.5092946269,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4925623091,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4988028731,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4973887093,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4763038819,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4789501413,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115409\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4834888556,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.481000481,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4747211014,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4999250112,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.5086211281,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4749465685,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4895960832,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.5004003203,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.504642713,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4726568039,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.547465236,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4995504046,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4869734599,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.5035500277,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4891889248,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4881382407,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.5195614901,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4835122329,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4964503798,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.482602191,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    }, {\r\n"
			+ "      \"downloadSpeed\" : 0.4940711462,\r\n" + "      \"rssiValue\" : -43,\r\n"
			+ "      \"speedInMbps\" : 300,\r\n" + "      \"timeStamp\" : \"1555115410\"\r\n" + "    } ]";

	@PostConstruct
	public void init() {

		NetworkData[] networkDatas = Utilities.unmarshall(JerseyClient.sendGetResponse(
				"http://localhost:8080/networkanalyzer/", "rest/master/readNetworkDataTable"), NetworkData[].class);

		
		// TODO: read from another table
		networkDataSummary = Utilities.unmarshall(
				JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/", "rest/master/analyze"),
				NetworkDataSummary.class);

		createLineModel(networkDatas);
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
			values.add(netData.getDownloadSpeed());
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

	public NetworkDataSummary getNetworkDataSummary() {
		return networkDataSummary;
	}

	public void setNetworkDataSummary(NetworkDataSummary networkDataSummary) {
		this.networkDataSummary = networkDataSummary;
	}

}
