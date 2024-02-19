package com.serioussem.phgim.school.presentation.ui.screens.class_schedule
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.serioussem.phgim.school.R
import com.serioussem.phgim.school.domain.model.DayOfWeekModel
import com.serioussem.phgim.school.domain.model.LessonModel
import com.serioussem.phgim.school.presentation.ui.components.AppScaffold
import com.serioussem.phgim.school.presentation.ui.components.AppTopBar
import com.serioussem.phgim.school.presentation.ui.components.ErrorDialog
import com.serioussem.phgim.school.presentation.ui.components.HorizontalDivider
import com.serioussem.phgim.school.presentation.ui.components.ScreenProgress
import com.serioussem.phgim.school.presentation.ui.components.VerticalDivider
import com.serioussem.phgim.school.presentation.ui.theme.White99
import com.serioussem.phgim.school.utils.MapKeys.DAY_INDEX_MAP_KEY
import com.serioussem.phgim.school.utils.MapKeys.LESSON_INDEX_MAP_KEY
import com.serioussem.phgim.school.utils.MapKeys.NAV_CONTROLLER_MAP_KEY
import com.serioussem.phgim.school.utils.findActivity

@Composable
fun ClassScheduleScreen(
    viewModel: ClassScheduleViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.viewState.value
    val context = LocalContext.current
    BackHandler(onBack = {
        context.findActivity()?.finish()
    })
    AppScaffold(
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.class_schedule_screen_title),
                navigationIcon = {},
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            WeekTitleNavigation(
                title = state.weekDateRange,
                isShowNextWeekButton = state.isShowNextWeekButton,
                isShowPreviousWeekButton = state.isShowPreviousWeekButton,
                nextWeekButtonAction = {
                    viewModel.setEvent(
                        event = ClassScheduleScreenContract.Event.NEXT_WEEK,
                        data = null
                    )
                },
                previousWeekButtonAction = {
                    viewModel.setEvent(
                        event = ClassScheduleScreenContract.Event.PREVIOUS_WEEK,
                        data = null
                    )
                },
            )
            if (state.isLoading) {
                ScreenProgress()
            } else if (state.error != null) {
                ErrorDialog(errorMessage = state.error) {
                    viewModel.setEvent(
                        event = ClassScheduleScreenContract.Event.CLOSE_DIALOG,
                        data = null
                    )
                }
            } else {
                ClassSchedule(
                    currentDayIndex = state.currentDayIndex,
                    daysOfWeek = state.daysOfWeek,
                    openLessonScreen = { dayIndex, lessonModel ->
                        viewModel.setEvent(
                            event = ClassScheduleScreenContract.Event.OPEN_LESSON_SCREEN,
                            data = mapOf(
                                NAV_CONTROLLER_MAP_KEY to navController,
                                DAY_INDEX_MAP_KEY to dayIndex,
                                LESSON_INDEX_MAP_KEY to lessonModel,
                            )
                        )
                    },
                )
            }
        }
    }

}

@Composable
private fun WeekTitleNavigation(
    title: String,
    isShowNextWeekButton: Boolean,
    isShowPreviousWeekButton: Boolean,
    nextWeekButtonAction: () -> Unit,
    previousWeekButtonAction: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isShowPreviousWeekButton) {
            IconButton(
                onClick = previousWeekButtonAction,
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
        } else {
            Box(modifier = Modifier.size(32.dp))
        }
        Text(
            text = title
        )
        if (isShowNextWeekButton) {
            IconButton(
                onClick = nextWeekButtonAction,
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
        } else {
            Box(modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun ClassSchedule(
    currentDayIndex: Int,
    daysOfWeek: List<DayOfWeekModel>,
    openLessonScreen: (dayIndex: Int, lessonIndex: Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState(initialFirstVisibleItemIndex = currentDayIndex),
    ) {
        itemsIndexed(daysOfWeek) { dayIndex, dayOfWeek ->
            DayOfWeekCard(
                dayName = stringArrayResource(id = R.array.days_of_week)[dayIndex],
                dayIndex = dayIndex,
                lessonsOfDay = dayOfWeek.lessonsOfDay,
                openLessonScreen = openLessonScreen,
            )
        }
    }
}

@Composable
fun DayOfWeekCard(
    dayName: String,
    dayIndex: Int,
    lessonsOfDay: List<LessonModel>,
    openLessonScreen: (dayIndex: Int, lessonIndex: Int) -> Unit,
) {
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
            for ((index, lesson) in lessonsOfDay.withIndex()) {
                LessonItem(
                    dayIndex = dayIndex,
                    lessonIndex = index,
                    lessonModel = lesson,
                    openLessonScreen = openLessonScreen,
                )
            }
        }
    }
}

@Composable
fun LessonItem(
    dayIndex: Int,
    lessonIndex: Int,
    lessonModel: LessonModel,
    openLessonScreen: (dayIndex: Int, lessonIndex: Int) -> Unit,
) {
    Column(modifier = Modifier) {
        OutlinedButton(
            onClick = { openLessonScreen(dayIndex, lessonIndex) },
            modifier = Modifier.fillMaxWidth(),
            border = null,
            shape = RoundedCornerShape(0.dp),
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
                    text = lessonModel.lessonName,
                    modifier = Modifier
                        .weight(0.25f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    color = DarkGray
                )
                VerticalDivider()
                Text(
                    text = lessonModel.homeWork,
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = DarkGray
                )
                VerticalDivider()
                Text(
                    text = lessonModel.mark,
                    modifier = Modifier.weight(0.15f),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    color = DarkGray
                )
            }
        }
        HorizontalDivider()
    }
}

