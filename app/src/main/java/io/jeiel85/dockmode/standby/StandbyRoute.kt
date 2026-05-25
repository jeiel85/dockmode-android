package io.jeiel85.dockmode.standby

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            viewModel.refreshGalleryPhotos()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.refreshGalleryPhotos()
    }

    StandbyScreen(
        state = state,
        onClockStyleChanged = viewModel::setClockStyle,
        onThemeChanged = viewModel::setSelectedThemeId,
        onTriggerGalleryPermission = {
            galleryLauncher.launch(permissionToRequest)
        },
    )
}
