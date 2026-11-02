package mobappdev.example.nback_cimpl.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mobappdev.example.nback_cimpl.ui.viewmodels.FakeVM
import mobappdev.example.nback_cimpl.ui.viewmodels.GameVM
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel


@Composable
fun GameScreen (
    vm: GameViewModel
) {
    val highscore by vm.highscore.collectAsState()  // Highscore is its own StateFlow
    val gameState by vm.gameState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {

                items(9) { index ->
                    Box (
                        modifier = Modifier
                            .aspectRatio(1f)
                            .border(3.dp, Color.Black, RoundedCornerShape(16.dp))
                            .background(Color.Cyan, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                            Text(text = "$index")
                    }
                }

            }
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                    Arrangement.Center,
                    Alignment.Top
            )
            {
                    Button(onClick = { /*TODO*/ }) {
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
        GameScreen(FakeVM())
    }
}