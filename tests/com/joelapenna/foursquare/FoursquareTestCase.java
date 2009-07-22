/**
 * Copyright 2009 Joe LaPenna
 */

package com.joelapenna.foursquare;

import com.joelapenna.foursquare.error.FoursquareException;
import com.joelapenna.foursquare.types.Auth;
import com.joelapenna.foursquare.types.Credentials;
import com.joelapenna.foursquare.types.Group;
import com.joelapenna.foursquare.types.User;
import com.joelapenna.foursquare.types.Venue;

import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author Joe LaPenna (joe@joelapenna.com)
 */
public class FoursquareTestCase extends TestCase {

    /**
     * hasCredentials tests for only phone/password unless the user specifies true as a parameter.
     * In that case, hasCredentials will makes ure that the foursquare instance has an oauth_token
     * and oauth_token_secret, usually acquired through authExchange().
     */
    @SmallTest
    public void test_hasCredentials() {
        Foursquare foursquare = new Foursquare(TestCredentials.oAuthConsumerKey,
                TestCredentials.oAuthConsumerSecret);
        assertFalse(foursquare.hasCredentials());

        foursquare.setCredentials("phone", "password");
        assertTrue(foursquare.hasCredentials());

        // check for v1/oauth credentials
        assertFalse(foursquare.hasCredentials(true));

        foursquare.setCredentials("a", "b", "c", "d");
        assertTrue(foursquare.hasCredentials(true));

    }

    @LargeTest
    public void test_authExchange() throws FoursquareException, IOException {
        Foursquare foursquare = new Foursquare(TestCredentials.oAuthConsumerKey,
                TestCredentials.oAuthConsumerSecret);
        foursquare.setCredentials(TestCredentials.testFoursquarePhone,
                TestCredentials.testFoursquarePassword);

        Credentials credentials = foursquare.authExchange();

        assertNotNull(credentials.getOauthToken());
        assertNotNull(credentials.getOauthTokenSecret());
    }

    @LargeTest
    public void test_login() throws FoursquareException, IOException {
        Foursquare foursquare = getFoursquareForTest();

        Auth auth = foursquare.login();
        assertNotNull(auth);
        assertEquals("testuser@joelapenna.com", auth.getEmail());
        assertEquals("9711", auth.getId());
    }

    @LargeTest
    public void test_todos() throws FoursquareException, IOException {
        Foursquare foursquare = getFoursquareForTest();

        Group todos = foursquare.todos("23", null, null);
        assertNotNull(todos);
    }

    @LargeTest
    public void test_user() throws FoursquareException, IOException {
        Foursquare foursquare = getFoursquareForTest();

        User user = foursquare.user();
        assertNotNull(user);
    }

    @LargeTest
    public void test_venue() throws FoursquareException, IOException {
        Foursquare foursquare = getFoursquareForTest();

        Venue venue = foursquare.venue("23035");
        assertNotNull(venue);
    }

    @LargeTest
    public void test_venues() throws FoursquareException, IOException {
        Foursquare foursquare = getFoursquareForTest();

        Group venues = foursquare.venues("coffee", null, null, 1, 10);
        assertNotNull(venues);
    }

    private static final Foursquare getFoursquareForTest() {
        Foursquare foursquare = new Foursquare();
        foursquare.setCredentials(TestCredentials.testFoursquarePhone,
                TestCredentials.testFoursquarePassword);
        return foursquare;
    }
}