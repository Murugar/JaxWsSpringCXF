package com.iqmsoft.jaxws.cxf.client;

import java.security.KeyStore;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.log4j.Logger;

import com.iqmsoft.jaxws.cxf.model.Player;
import com.iqmsoft.jaxws.cxf.ws.ITeamService;

public class Main {

	public static final Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		properties.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));

		// client
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(ITeamService.class);
		jaxWsProxyFactoryBean.setAddress(properties.getProperty("endpoint"));
		ITeamService teamServiceClient = (ITeamService) jaxWsProxyFactoryBean.create();

		logger.info("getTeam");
		List<Player> team = teamServiceClient.getTeam();
		for (Player player : team) {
			logger.info("       " + player.getNumber() + " : " + player.getName() + " (" + player.getAge() + ")");
		}

		logger.info("\n updatePlayerByNumber");
		teamServiceClient.updatePlayerByNumber(1, new Player(1, "Anders Lindegaard", 28));

		logger.info("\n foo");
		teamServiceClient.foo();
		
		
		logger.info("\n deletePlayer");
		teamServiceClient.deletePlayer(6);

		logger.info("\n getPlayers");
		team = teamServiceClient.getPlayers(1, 3, 6);
		for (Player player : team) {
			logger.info("       " + player.getNumber() + " : " + player.getName() + " (" + player.getAge() + ")");
		}
	}

}