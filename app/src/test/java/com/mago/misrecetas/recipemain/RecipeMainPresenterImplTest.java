package com.mago.misrecetas.recipemain;

import com.mago.misrecetas.BaseTest;
import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.libs.base.EventBus;
import com.mago.misrecetas.recipemain.events.RecipeMainEvent;
import com.mago.misrecetas.recipemain.ui.interactor.GetNextRecipeInteractor;
import com.mago.misrecetas.recipemain.ui.interactor.SaveRecipeInteractor;
import com.mago.misrecetas.recipemain.ui.presenter.RecipeMainPresenterImpl;
import com.mago.misrecetas.recipemain.ui.view.RecipeMainView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jorgemartinez on 29/11/18.
 */
@RunWith(RobolectricTestRunner.class)
public class RecipeMainPresenterImplTest extends BaseTest {
    @Mock
    private EventBus eventBus;
    @Mock
    private RecipeMainView view;
    @Mock
    private SaveRecipeInteractor saveRecipeInteractor;
    @Mock
    private GetNextRecipeInteractor getNextRecipeInteractor;
    @Mock
    private Recipe recipe;
    @Mock
    private RecipeMainEvent event;

    private RecipeMainPresenterImpl presenter;



    @Override
    public void setUp() throws Exception {
        super.setUp();

        presenter = new RecipeMainPresenterImpl(eventBus, view, saveRecipeInteractor, getNextRecipeInteractor);
    }

    @Test
    public void testOnCreate_subscribedToEventBus() {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void testOnDestroy_UnSubscribedToEventBus() {
        presenter.onDestroy();
        verify(eventBus).unregister(presenter);
        assertNull(presenter.getView());
    }

    @Test
    public void testSaveRecipe_executeSaveInteractor() {
        presenter.saveRecipe(recipe);

        assertNotNull(presenter.getView());
        verify(view).likeAnimation();
        verify(view).hideUIElements();
        verify(view).showProgressBar();

        verify(saveRecipeInteractor).execute(recipe);
    }

    @Test
    public void testDismissRecipe_executeGetNextRecipeInteractor() {
        presenter.dismissRecipe();

        assertNotNull(presenter.getView());
        verify(view).dislikeAnimation();
    }

    @Test
    public void testGetNextRecipe_executeGetNextRecipeInteractor() {
        presenter.getNextRecipe();

        assertNotNull(presenter.getView());
        verify(view).hideUIElements();
        verify(view).showProgressBar();

        getNextRecipeInteractor.execute();
    }

    @Test
    public void testOnEvent_hasError() {
        String errMsg = "error";

        when(event.getError()).thenReturn(errMsg);
        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).hideProgressBar();
        verify(view).onGetRecipeError(event.getError());
    }

    @Test
    public void testOnSaveEvent_notifyViewAndCallGetNextRecipeInteractor() {
        when(event.getType()).thenReturn(RecipeMainEvent.SAVE_EVENT);
        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).onRecipeSaved();
        verify(getNextRecipeInteractor).execute();
    }

    @Test
    public void testOnNextEvent_setRecipeOnView() {
        when(event.getType()).thenReturn(RecipeMainEvent.NEXT_EVENT);
        when(event.getRecipe()).thenReturn(recipe);
        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).setRecipe(event.getRecipe());

    }

    @Test
    public void testImageReady_updateUI() {
        presenter.imageReady();

        assertNotNull(presenter.getView());
        verify(view).hideProgressBar();
        verify(view).showUIElements();
    }

    @Test
    public void testImageError_updateUI() {
        String error = "error";

        presenter.imageError(error);

        assertNotNull(presenter.getView());
        verify(view).onGetRecipeError(error);
    }

    @Test
    public void testGetView_returnsView() {
        assertEquals(view, presenter.getView());
    }
}