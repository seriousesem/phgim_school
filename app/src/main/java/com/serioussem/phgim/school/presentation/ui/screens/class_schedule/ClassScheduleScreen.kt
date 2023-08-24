package com.serioussem.phgim.school.presentation.ui.screens.class_schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.serioussem.phgim.school.R
import com.serioussem.phgim.school.domain.model.DayOfWeek
import com.serioussem.phgim.school.domain.model.Lesson
import com.serioussem.phgim.school.presentation.ui.components.AppScaffold
import com.serioussem.phgim.school.presentation.ui.components.AppTopBar
import com.serioussem.phgim.school.presentation.ui.components.HorizontalDivider
import com.serioussem.phgim.school.presentation.ui.components.MenuIconButton
import com.serioussem.phgim.school.presentation.ui.components.VerticalDivider
import com.serioussem.phgim.school.presentation.ui.theme.White99

@Composable
fun ClassScheduleScreen(
    viewModel: ClassScheduleViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = false) {
//        viewModel.fetchJournal()
    }
    val state = viewModel.viewState.value
    AppScaffold(
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.class_schedule_screen_title),
                navigationIcon = {
                    MenuIconButton()
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            WeekTitleNavigation(title = state.currentWeek)
            ClassScheduleList(
                currentDayIndex = state.currentDayIndex,
                daysOfWeek = state.daysOfWeek
            )
        }
    }

}

@Composable
private fun WeekTitleNavigation(title: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = DarkGray,
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            text = title
        )
        IconButton(
            onClick = { /*TODO*/ },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = DarkGray,
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun ClassScheduleList(currentDayIndex: Int, daysOfWeek: List<DayOfWeek>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState(initialFirstVisibleItemIndex = currentDayIndex),
    ) {
        itemsIndexed(daysOfWeek) { dayIndex, dayOfWeek ->
            DayOfWeekCard(
                dayName = stringArrayResource(id = R.array.days_of_week)[dayIndex],
                dayIndex = dayIndex,
                lessonsOfDay = dayOfWeek.lessonsOfDay
            )
        }
    }
}

@Composable
fun DayOfWeekCard(dayName: String, dayIndex: Int, lessonsOfDay: List<Lesson>) {
    Card(
        modifier = Modifier.padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = White99
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayName,
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGray

            )
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.lesson_title),
                    modifier = Modifier.weight(0.25f),
                    textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                    color = DarkGray
                )
                VerticalDivider()
                Text(
                    text = stringResource(id = R.string.home_work_title),
                    modifier = Modifier.weight(0.6f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )
                VerticalDivider()
                Text(
                    text = stringResource(id = R.string.mark_title),
                    modifier = Modifier.weight(0.15f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )
            }
            HorizontalDivider()
            for ((lessonIndex, lesson) in lessonsOfDay.withIndex()) {
                LessonItem(
                    dayIndex = dayIndex,
                    lessonIndex = lessonIndex,
                    lesson = lesson
                )
            }
        }
    }
}

@Composable
fun LessonItem(
    dayIndex: Int,
    lessonIndex: Int,
    lesson: Lesson,
) {
    Column(modifier = Modifier) {
        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            border = null,
            contentPadding = PaddingValues(0.dp),
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = lesson.lessonName,
                    modifier = Modifier.weight(0.25f),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    color = Gray
                )
                VerticalDivider()
                Text(
                    text = lesson.homeWork,
                    modifier = Modifier.weight(0.6f),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    color = Gray
                )
                VerticalDivider()
                Text(
                    text = lesson.mark,
                    modifier = Modifier.weight(0.15f),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    color = Gray
                )
            }
        }
        HorizontalDivider()
    }
}
