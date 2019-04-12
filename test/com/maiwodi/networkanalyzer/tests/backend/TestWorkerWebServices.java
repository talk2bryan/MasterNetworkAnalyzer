package com.maiwodi.networkanalyzer.tests.backend;

import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.maiwodi.networkanalyzer.app.utils.JerseyClient;

class TestWorkerWebServices {

	private static final Logger LOGGER = LogManager.getLogger(TestWorkerWebServices.class.getName());

	String jsonStr = "[ {\r\n" + "\r\n" + "        \"rssiValue\" : -55,\r\n" + "        \"speedInMbps\" : 72,\r\n"
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

	@Test
	void testNetworkDataAnalysis() {
//		Response response = JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/",
//				"rest/worker/post/data", jsonStr);

		Response response = JerseyClient.sendPostResponse("http://172.31.31.231:8080/networkanalyzer-1.0-SNAPSHOT/",
				"rest/worker/post/data", jsonStr);

		LOGGER.debug("Response: {}", response.readEntity(String.class));

		assertEquals(response.getStatus(), 200);

	}

}
