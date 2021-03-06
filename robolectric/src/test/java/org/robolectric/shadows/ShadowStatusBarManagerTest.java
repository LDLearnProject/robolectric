package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.Q;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.google.common.truth.Truth.assertThat;

import android.app.StatusBarManager;
import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.util.ReflectionHelpers;
import org.robolectric.util.ReflectionHelpers.ClassParameter;

/** Unit tests for {@link ShadowStatusBarManager}. */
@RunWith(AndroidJUnit4.class)
public final class ShadowStatusBarManagerTest {

  @Test
  public void getDisable() throws ClassNotFoundException {
    callDisableMethodofStatusBarManager(ShadowStatusBarManager.DEFAULT_DISABLE_MASK);
    assertThat(
            ((ShadowStatusBarManager)
                    Shadow.extract(
                        getApplicationContext().getSystemService(Context.STATUS_BAR_SERVICE)))
                .getDisableFlags())
        .isEqualTo(ShadowStatusBarManager.DEFAULT_DISABLE_MASK);
  }

  @Test
  @Config(minSdk = M)
  public void getDisable2() throws ClassNotFoundException {
    callDisable2MethodofStatusBarManager(ShadowStatusBarManager.DEFAULT_DISABLE2_MASK);
    assertThat(
            ((ShadowStatusBarManager)
                    Shadow.extract(
                        getApplicationContext().getSystemService(Context.STATUS_BAR_SERVICE)))
                .getDisable2Flags())
        .isEqualTo(ShadowStatusBarManager.DEFAULT_DISABLE2_MASK);
  }

  @Test
  @Config(minSdk = Q)
  public void setDisabledForSetup() {
    getApplicationContext().getSystemService(StatusBarManager.class).setDisabledForSetup(true);
    assertThat(
            ((ShadowStatusBarManager)
                    Shadow.extract(
                        getApplicationContext().getSystemService(Context.STATUS_BAR_SERVICE)))
                .getDisableFlags())
        .isEqualTo(StatusBarManager.DEFAULT_SETUP_DISABLE_FLAGS);
    assertThat(
            ((ShadowStatusBarManager)
                    Shadow.extract(
                        getApplicationContext().getSystemService(Context.STATUS_BAR_SERVICE)))
                .getDisable2Flags())
        .isEqualTo(StatusBarManager.DEFAULT_SETUP_DISABLE2_FLAGS);

    getApplicationContext().getSystemService(StatusBarManager.class).setDisabledForSetup(false);
    assertThat(
            ((ShadowStatusBarManager)
                    Shadow.extract(
                        getApplicationContext().getSystemService(Context.STATUS_BAR_SERVICE)))
                .getDisableFlags())
        .isEqualTo(StatusBarManager.DISABLE_NONE);
    assertThat(
            ((ShadowStatusBarManager)
                    Shadow.extract(
                        getApplicationContext().getSystemService(Context.STATUS_BAR_SERVICE)))
                .getDisable2Flags())
        .isEqualTo(StatusBarManager.DISABLE2_NONE);
  }

  private static void callDisableMethodofStatusBarManager(int disableFlags)
      throws ClassNotFoundException {
    ReflectionHelpers.callInstanceMethod(
        Class.forName("android.app.StatusBarManager"),
        getApplicationContext().getSystemService(Context.STATUS_BAR_SERVICE),
        "disable",
        ClassParameter.from(int.class, disableFlags));
  }

  private static void callDisable2MethodofStatusBarManager(int disable2Flags)
      throws ClassNotFoundException {
    ReflectionHelpers.callInstanceMethod(
        Class.forName("android.app.StatusBarManager"),
        getApplicationContext().getSystemService(Context.STATUS_BAR_SERVICE),
        "disable2",
        ClassParameter.from(int.class, disable2Flags));
  }
}
