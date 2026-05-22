package io.jeiel85.dockmode.standby

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.jeiel85.dockmode.AppContainer

@Composable
fun StandbyRoute(
    appContainer: AppContainer,
    @Suppress("UNUSED_PARAMETER") mode: StandbyLaunchMode = StandbyLaunchMode.Activity,
) {
    val viewModel: StandbyViewModel = viewModel(factory = StandbyViewModel.Factory(appContainer))
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    StandbyScreen(state = state)
}
