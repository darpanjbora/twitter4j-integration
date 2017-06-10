/*
  Copyright 2017 Darpan Jyoti Bora

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package twitterapidemo;

/**
 *|A java project to demonstrate Twitter4j API|
 * Post tweet on twitter
 * 
 * @author darpanjyotibora
 */
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
        
public class TwitterAPIDemo {
        //Enter the consumer key , consumer secret key, access token and access token secret by generating from the twiiter developers site.
        private static final String consumerKey = "********************";
        static private final String consumerSecret = "***********************";
        static private final String accessToken = "**********-***********";
        static private final String accessTokenSecret = "********************";
        
        
    public static void main(String[] args) throws IOException, TwitterException {
       
        //TwitterAPIDemo twitterApiDemo = new TwitterAPIDemo();
        
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);
        Configuration configuration = builder.build();
        
        TwitterFactory twitterFactory = new TwitterFactory(configuration);
        Twitter twitter = twitterFactory.getInstance();
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice:\n1. To post tweet\n2.To search tweets\n3. Recent top 3 trends and number of posts of each trending topic");
        int choice = sc.nextInt();
        switch(choice){
            case 1: 
                    System.out.println("What's happening: ");
                    String post = sc.next();
                    StatusUpdate statusUpdate = new StatusUpdate(post+"-Posted by TwitterAPI");
                   Status status = twitter.updateStatus(statusUpdate);

                    System.out.println("status.toString() = " + status.toString());
                    System.out.println("status.getInReplyToScreenName() = " + status.getInReplyToScreenName());
                    System.out.println("status.getSource() = " + status.getSource());
                    System.out.println("status.getText() = " + status.getText());
                    System.out.println("status.getContributors() = " + Arrays.toString(status.getContributors()));
                    System.out.println("status.getCreatedAt() = " + status.getCreatedAt());
                    System.out.println("status.getCurrentUserRetweetId() = " + status.getCurrentUserRetweetId());
                    System.out.println("status.getGeoLocation() = " + status.getGeoLocation());
                    System.out.println("status.getId() = " + status.getId());
                    System.out.println("status.getInReplyToStatusId() = " + status.getInReplyToStatusId());
                    System.out.println("status.getInReplyToUserId() = " + status.getInReplyToUserId());
                    System.out.println("status.getPlace() = " + status.getPlace());
                    System.out.println("status.getRetweetCount() = " + status.getRetweetCount());
                    System.out.println("status.getRetweetedStatus() = " + status.getRetweetedStatus());
                    System.out.println("status.getUser() = " + status.getUser());
                    System.out.println("status.getAccessLevel() = " + status.getAccessLevel());
                    System.out.println("status.getHashtagEntities() = " + Arrays.toString(status.getHashtagEntities()));
                    System.out.println("status.getMediaEntities() = " + Arrays.toString(status.getMediaEntities()));
                    if(status.getRateLimitStatus() != null)
                    {
                        System.out.println("status.getRateLimitStatus().getLimit() = " + status.getRateLimitStatus().getLimit());
                        System.out.println("status.getRateLimitStatus().getRemaining() = " + status.getRateLimitStatus().getRemaining());
                        System.out.println("status.getRateLimitStatus().getResetTimeInSeconds() = " + status.getRateLimitStatus().getResetTimeInSeconds());
                        System.out.println("status.getRateLimitStatus().getSecondsUntilReset() = " + status.getRateLimitStatus().getSecondsUntilReset());
                    }
                    System.out.println("status.getURLEntities() = " + Arrays.toString(status.getURLEntities()));
                    System.out.println("status.getUserMentionEntities() = " + Arrays.toString(status.getUserMentionEntities()));
                break;
            case 2: 
                System.out.println("Enter keyword");
                String keyword = sc.next();
                try{
                        Query query = new Query(keyword);
                        QueryResult result;
                        do {
                            result = twitter.search(query);
                            List<Status> tweets = result.getTweets();
                            for (Status tweet : tweets) {
                                System.out.println(tweet.getCreatedAt()+":\t@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                            }
                        } while ((query = result.nextQuery()) != null);
                        System.exit(0);
                    }catch (TwitterException te) {
                        System.out.println("Failed to search tweets: " + te.getMessage());
                        System.exit(-1);
                            break;
                    }
            case 3:
                //WOEID for India = 23424848
                Trends trends = twitter.getPlaceTrends(23424848);
                int count = 0;
                for(Trend trend : trends.getTrends()){
                    if(count<3){
                  Query query = new Query(trend.getName());
                  QueryResult result;
                  int numberofpost=0;
                        do {
                            result = twitter.search(query);
                            List<Status> tweets = result.getTweets();
                            for (Status tweet : tweets) {
                                numberofpost++;
                              }
                            }while ((query = result.nextQuery()) != null);
                        System.out.println("Number of post for the topic '"+trend.getName()+"' is: "+numberofpost);
                  count++;
                  }else 
                        break;
                }
                break;
            default: 
                System.out.println("Invalid input");
            }
    }
}
