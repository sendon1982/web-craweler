package com.zunix.craweler.main;

import com.zunix.craweler.BaseCraweler;
import com.zunix.craweler.ImageCraweler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Main App class to run the crawling, this is the entry point.
 * 
 * @author sendon1982
 *
 */
public class MainApp
{
	public static void main(String[] args) throws Exception
	{
		String crawlStorageFolder = "/usr/local/crawl/";
		int numberOfCrawlers = 1;

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		/*
		 * For each crawl, you need to add some seed urls. These are the first URLs that are fetched and then the
		 * crawler starts following links which are found in these pages
		 */
		
		controller.addSeed("http://tieba.baidu.com/p/3568996375/");
		controller.addSeed("http://www.zhihu.com/question/22741307");
//		controller.addSeed("http://www.ics.uci.edu/~lopes/");
//		controller.addSeed("http://www.ics.uci.edu/~welling/");
//		controller.addSeed("http://www.ics.uci.edu/");

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code will reach the line after this only
		 * when crawling is finished.
		 */
		String[] domain = new String[]{
			"http://tieba.baidu.com",
			"http://www.zhihu.com"
		};
		ImageCraweler.configure(domain, crawlStorageFolder);
		
		controller.start(ImageCraweler.class, numberOfCrawlers);
	}
}
