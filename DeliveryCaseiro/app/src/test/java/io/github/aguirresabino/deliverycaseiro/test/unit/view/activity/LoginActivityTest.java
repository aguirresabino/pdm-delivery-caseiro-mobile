package io.github.aguirresabino.deliverycaseiro.test.unit.view.activity;

import android.util.Log;
import android.widget.Button;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import androidx.test.platform.app.InstrumentationRegistry;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.view.activity.LoginActivity;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {

    private LoginActivity loginActivity;

    @Before
    public void setUp() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        loginActivity = (LoginActivity) InstrumentationRegistry.getInstrumentation().newActivity(ClassLoader.getSystemClassLoader(), LoginActivity.class.getName(), null);
//        loginActivity = Robolectric.buildActivity(LoginActivity.class)
//                .create()
//                .resume()
//                .get();
    }

    @Test
    public void shouldNotNullActivity() {
        Assert.assertNotNull(loginActivity);
    }

    @Test
    public void testLoginButtonVerifyexpectedText() {
        Button loginButton = loginActivity.findViewById(R.id.activityLoginButtonEntrar);
        final String expectedText = loginActivity.getResources().getString(R.string.entrar);

        Assert.assertEquals(loginButton.getText(), expectedText);
    }

    @Test
    public void testLoginButtonClickVerifyexpectedText() {
        Button loginButton = loginActivity.findViewById(R.id.activityLoginButtonEntrar);
        // click
        loginButton.performClick();
        final String expectedText = loginButton.getResources().getString(R.string.entrar_aguarde);

        Assert.assertEquals(loginButton.getText(), expectedText);
    }

    @Test
    public void testCadastroButtonVerifyexpectedText() {
        Button cadastrarButton = loginActivity.findViewById(R.id.activityLoginButtonCrieSuaConta);
        final String expectedText = loginActivity.getResources().getString(R.string.crie_sua_conta);

        Assert.assertEquals(cadastrarButton.getText(), expectedText);
    }
}
