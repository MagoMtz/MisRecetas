package com.mago.misrecetas;

import android.app.Application;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

import androidx.test.core.app.ApplicationProvider;

/**
 * Created by jorgemartinez on 29/11/18.
 */
public abstract class BaseTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        if (ApplicationProvider.getApplicationContext() != null) {
            ShadowApplication shadowApp = Shadows.shadowOf((Application) ApplicationProvider.getApplicationContext());
            shadowApp.grantPermissions("android.permission.INTERNET");
        }

    }
}
