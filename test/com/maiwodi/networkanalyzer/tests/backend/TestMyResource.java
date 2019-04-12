package com.maiwodi.networkanalyzer.tests.backend;

import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.maiwodi.networkanalyzer.app.utils.JerseyClient;

class TestMyResource {

	private static final Logger LOGGER = LogManager.getLogger(TestMyResource.class.getName());

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

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	// @Test
	// @Ignore
	// void testAddWorker() {
	// fail("Not yet implemented");
	// }

	@Test
	void testPostDataForAnalysis() {
		Response response = JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/",
				"rest/myresource/post/data", jsonStr);

		LOGGER.debug("Response: {}", response.readEntity(String.class));

		assertEquals(response.getStatus(), 200);
	}

	@Test
	void testCloudPostDataForAnalysis() {
		Response response = JerseyClient.sendPostResponse(
				"http://ec2-3-94-173-58.compute-1.amazonaws.com:8080/networkanalyzer-1.0-SNAPSHOT/",
				"rest/myresource/post/data", jsonStr);

		LOGGER.debug("Response: {}", response.readEntity(String.class));

		assertEquals(response.getStatus(), 200);
	}

}
