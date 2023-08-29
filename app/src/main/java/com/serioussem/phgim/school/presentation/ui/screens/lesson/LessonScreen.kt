package com.serioussem.phgim.school.presentation.ui.screens.lesson
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.serioussem.phgim.school.R
import com.serioussem.phgim.school.domain.model.LessonModel
import com.serioussem.phgim.school.presentation.ui.components.AppScaffold
import com.serioussem.phgim.school.presentation.ui.components.AppTopBar
import com.serioussem.phgim.school.presentation.ui.components.BackIconButton
import com.serioussem.phgim.school.presentation.ui.components.ErrorDialog
import com.serioussem.phgim.school.presentation.ui.components.HorizontalDivider
import com.serioussem.phgim.school.presentation.ui.components.MenuIconButton
import com.serioussem.phgim.school.presentation.ui.components.ScreenProgress
import com.serioussem.phgim.school.presentation.ui.theme.White99

@Composable
fun LessonScreen(
    navController: NavController,
    viewModel: LessonViewModel = hiltViewModel(),
) {

    val state = viewModel.viewState.value
    BackHandler(enabled = true) {
        navController.popBackStack()
    }
    AppScaffold(
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.home_work_title),
                navigationIcon = {
                    BackIconButton(navController = navController)
                },
                actionIcon = {
                    MenuIconButton(action = {})
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                ScreenProgress()
            } else if (state.error != null) {
                ErrorDialog(errorMessage = state.error) {
                    viewModel.setEvent(
                        event = LessonScreenContract.Event.CLOSE_DIALOG,
                        data = null
                    )
                }
            } else {
                LessonCard(
                    lessonName = state.lessonName,
                    homeWork = state.homeWork,
                    hyperlinks = state.hyperlinks,
                )
            }
        }
    }

}

@Composable
fun LessonCard(lessonName: String, homeWork: String,  hyperlinks: List<String>,) {
    Card(
        modifier = Modifier.padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = White99
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                text = lessonName,
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGray

            )
            HorizontalDivider()
            TextWithHyperlinks(
                fullText = homeWork,
                hyperlinks = hyperlinks,
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                fontSize = 14.sp,
            )
        }
    }
}
@Composable
private fun TextWithHyperlinks(
    modifier: Modifier = Modifier,
    fullText: String,
    hyperlinks: List<String>,
    linkTextColor: Color = Color.Blue,
    linkTextFontWeight: FontWeight = FontWeight.Medium,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        hyperlinks.forEachIndexed { index, link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize,
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = { offset ->
            annotatedString
                .getStringAnnotations("URL", offset, offset)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}

