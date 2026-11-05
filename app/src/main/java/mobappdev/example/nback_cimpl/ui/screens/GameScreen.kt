package mobappdev.example.nback_cimpl.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import mobappdev.example.nback_cimpl.ui.viewmodels.FakeVM
import mobappdev.example.nback_cimpl.ui.viewmodels.GameVM
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel


@Composable
fun GameScreen (
    vm: GameViewModel,
    onGameEnd: () -> Unit
) {
    val highscore by vm.highscore.collectAsState()  // Highscore is its own StateFlow
    val gameState by vm.gameState.collectAsState()
    val gameScore by vm.score.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var buttonColor by remember { mutableStateOf(true) }


    Scaffold (
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (gameState.currentEvent == 10) {
                Button(onClick = onGameEnd) {
                    Text(text = "Homepage")
                }

            }

            Text(
                modifier = Modifier.padding(32.dp),
                text = "High-Score = $highscore",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Score = $gameScore",
                style = MaterialTheme.typography.headlineLarge,
                color = if (buttonColor) Color.Black else Color.Red
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Event #: " + gameState.currentEvent,
                style = MaterialTheme.typography.headlineLarge,
                color = if (buttonColor) Color.Black else Color.Red
            )
            if (gameState.gameType.name == "Visual")
                LazyVerticalGrid(columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp)
                ) {

                    items(9) { index ->
                        Box (
                            modifier = Modifier
                                .aspectRatio(1f)
                                .border(3.dp, Color.Black, RoundedCornerShape(16.dp))
                                .background(
                                    if (gameState.eventValue == (index + 1)) Color.Red else Color.Cyan,
                                    RoundedCornerShape(16.dp)
                                )
                                .shadow(
                                    if (gameState.eventValue == (index + 1)) 6.dp else 0.dp,
                                    RoundedCornerShape(16.dp)
                                )
                            ,

                        )
                    }

                }
            if (gameState.gameType.name == "Audio")
                Text(text = "Listen and match correctly")
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                    Arrangement.Center,
                    Alignment.Top
            )
            {
                    Button(onClick = { buttonColor = vm.checkMatch() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                        Text(text = "Match")
                    }
            }
            
        }




    }

}




@Preview
@Composable
fun GameScreenPreview () {
    Surface() {
        GameScreen(FakeVM(), onGameEnd = {})
    }
}