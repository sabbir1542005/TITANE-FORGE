package com.example.presentation.ui

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Player
import com.example.data.model.Team
import com.example.data.model.Tournament
import com.example.presentation.viewmodel.DashboardUiState
import com.example.presentation.viewmodel.ForgeDashboardViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: ForgeDashboardViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTeamId by viewModel.selectedTeamId.collectAsState()
    val players by viewModel.selectedTeamPlayers.collectAsState()
    val isAuthenticated by viewModel.userAuthenticated.collectAsState()

    // Form states
    var showForm by remember { mutableStateOf(false) }
    var teamName by remember { mutableStateOf("") }
    var teamTag by remember { mutableStateOf("") }
    var teamGame by remember { mutableStateOf("Valorant") }
    var teamRegion by remember { mutableStateOf("North America") }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(TitanBackground),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text(
                                    text = "SYSTEM ENVIRONMENT",
                                    color = TitanTextMuted,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    letterSpacing = 1.5.sp
                                )
                                Text(
                                    text = "TITANE FORGE",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    letterSpacing = (-0.5).sp
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Auth Toggle Chip (Configure Route Protection Mock State)
                            FilterChip(
                                selected = isAuthenticated,
                                onClick = { viewModel.toggleAuth() },
                                label = {
                                    Text(
                                        text = if (isAuthenticated) "AUTHENTICATED" else "GUARD LOCKED",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = if (isAuthenticated) Icons.Default.Check else Icons.Default.Lock,
                                        contentDescription = "Auth Status Indicator",
                                        modifier = Modifier.size(12.dp)
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = TitanAccent.copy(alpha = 0.2f),
                                    selectedLabelColor = TitanAccent,
                                    selectedLeadingIconColor = TitanAccent,
                                    containerColor = TitanError.copy(alpha = 0.15f),
                                    labelColor = TitanError,
                                    iconColor = TitanError
                                ),
                                modifier = Modifier.testTag("auth_toggle_chip")
                            )

                            // Status LED Box: w-12 h-12 rounded-2xl bg-[#1C1B1F] border border-[#303033] flex items-center justify-center
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(TitanSurface)
                                    .border(1.dp, TitanBorder, RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.size(16.dp)
                                ) {
                                    // Pulse / Glow aura
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(androidx.compose.foundation.shape.CircleShape)
                                            .background(TitanAccent.copy(alpha = 0.35f))
                                    )
                                    // True LED
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .clip(androidx.compose.foundation.shape.CircleShape)
                                            .background(TitanAccent)
                                    )
                                }
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TitanBackground.copy(alpha = 0.95f)
                ),
                modifier = Modifier
                    .statusBarsPadding()
                    .drawBehind {
                        drawLine(
                            color = TitanBorder,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.radialGradient(
                        colors = listOf(TitanPrimary.copy(alpha = 0.05f), Color.Transparent),
                        center = Offset(800f, 200f),
                        radius = 1200f
                    )
                )
        ) {
            when (val state = uiState) {
                is DashboardUiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = TitanPrimary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("PREPARING FORGE SUITE...", color = TitanTextMuted, fontSize = 12.sp)
                    }
                }
                is DashboardUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error Logo",
                            tint = TitanError,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "COMPILATION ERROR",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = state.message,
                            color = TitanTextMuted,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    }
                }
                is DashboardUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("dashboard_lazy_column"),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // Title Display Card
                        item {
                            DashboardBanner()
                        }

                        // Core Stats Display Block
                        item {
                            StatsBlock(
                                totalTeams = state.teams.size,
                                activeTournaments = state.tournaments.filter { it.status == "Live" }.size,
                                prizePoolSum = state.tournaments.sumOf { it.prizePool }
                            )
                        }

                        // Team & Players Grid Layout (Canonical Adaptive split)
                        item {
                            Text(
                                text = "TEAMS COMMAND CENTRE",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            
                            // Horizontal lists of teams to select
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(state.teams) { team ->
                                    TeamSelectionCard(
                                        team = team,
                                        isSelected = team.id == selectedTeamId,
                                        onSelect = { viewModel.selectTeam(team.id) }
                                    )
                                }
                            }
                        }

                        // Selected Team Roster details
                        item {
                            val activeTeam = state.teams.firstOrNull { it.id == selectedTeamId }
                            PlayersRosterSection(
                                team = activeTeam,
                                roster = players
                            )
                        }

                        // Tournament tracking list
                        item {
                            Text(
                                text = "TOURNAMENTS REGISTRY",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                state.tournaments.forEach { tour ->
                                    TournamentCard(tournament = tour)
                                }
                            }
                        }

                        // Interactive creation simulation widget (gated by Auth protected state)
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("interactive_command_card"),
                                shape = RoundedCornerShape(32.dp),
                                colors = CardDefaults.cardColors(containerColor = TitanSurface),
                                border = BorderStroke(1.dp, TitanBorder)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "CONSTRUCT NEW CLAN",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            letterSpacing = 1.sp
                                        )
                                        IconButton(onClick = { showForm = !showForm }) {
                                            Icon(
                                                imageVector = if (showForm) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                                contentDescription = "Expand/Collapse Form",
                                                tint = TitanPrimary
                                            )
                                        }
                                    }

                                    AnimatedVisibility(visible = showForm) {
                                        Column(
                                            modifier = Modifier.padding(top = 12.dp),
                                            verticalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            if (!isAuthenticated) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clip(RoundedCornerShape(6.dp))
                                                        .background(TitanError.copy(alpha = 0.15f))
                                                        .padding(12.dp)
                                                ) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Icon(Icons.Default.Lock, contentDescription = "Security Alert", tint = TitanError, modifier = Modifier.size(16.dp))
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                        Text(
                                                            text = "Protected Route: Toggle 'AUTHENTICATED' on the navbar status to deploy changes.",
                                                            color = TitanError,
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                            }

                                            OutlinedTextField(
                                                value = teamName,
                                                onValueChange = { teamName = it },
                                                label = { Text("Team Name", color = TitanTextMuted) },
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedBorderColor = TitanPrimary,
                                                    unfocusedBorderColor = TitanBorder,
                                                    focusedLabelColor = TitanPrimary,
                                                    unfocusedLabelColor = TitanTextMuted,
                                                    focusedTextColor = Color.White,
                                                    unfocusedTextColor = Color.White
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .testTag("team_name_input")
                                            )

                                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                                OutlinedTextField(
                                                    value = teamTag,
                                                    onValueChange = { teamTag = it },
                                                    label = { Text("TAG (e.g. SEN)", color = TitanTextMuted) },
                                                    colors = OutlinedTextFieldDefaults.colors(
                                                        focusedBorderColor = TitanPrimary,
                                                        unfocusedBorderColor = TitanBorder,
                                                        focusedLabelColor = TitanPrimary,
                                                        unfocusedLabelColor = TitanTextMuted,
                                                        focusedTextColor = Color.White,
                                                        unfocusedTextColor = Color.White
                                                    ),
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .testTag("team_tag_input")
                                                )

                                                OutlinedTextField(
                                                    value = teamGame,
                                                    onValueChange = { teamGame = it },
                                                    label = { Text("Discipline/Game", color = TitanTextMuted) },
                                                    colors = OutlinedTextFieldDefaults.colors(
                                                        focusedBorderColor = TitanPrimary,
                                                        unfocusedBorderColor = TitanBorder,
                                                        focusedLabelColor = TitanPrimary,
                                                        unfocusedLabelColor = TitanTextMuted,
                                                        focusedTextColor = Color.White,
                                                        unfocusedTextColor = Color.White
                                                    ),
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .testTag("team_game_input")
                                                )
                                            }

                                            Button(
                                                onClick = {
                                                    if (teamName.isNotBlank() && teamTag.isNotBlank() && isAuthenticated) {
                                                        viewModel.addTeam(teamName, teamTag, teamGame, teamRegion)
                                                        teamName = ""
                                                        teamTag = ""
                                                        showForm = false
                                                    }
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(48.dp)
                                                    .testTag("submit_form_button"),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = TitanPrimary,
                                                    disabledContainerColor = TitanBorder
                                                ),
                                                enabled = isAuthenticated && teamName.isNotBlank() && teamTag.isNotBlank()
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Deploy Clan", modifier = Modifier.size(16.dp))
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text("DEPLOY CLAN RAG", color = Color.White, fontWeight = FontWeight.Bold)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(40.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = TitanSurface),
        border = BorderStroke(1.dp, TitanBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(TitanPrimary.copy(alpha = 0.08f), Color.Transparent)
                        )
                    )
                }
                .padding(20.dp)
        ) {
            Text(
                text = "Operational Workspace".uppercase(),
                color = TitanPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                letterSpacing = 1.5.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "TITANE FORGE INITIALIZED",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "The Next.js 15 Tailwind and Firebase repositories are pre-configured in /web. This Android presentation module provides live emulator controls to audit active team statistics.",
                color = TitanTextMuted,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun StatsBlock(
    totalTeams: Int,
    activeTournaments: Int,
    prizePoolSum: Double
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard(
            title = "CLANS ACTIVE",
            value = totalTeams.toString(),
            icon = Icons.Default.Person,
            color = TitanPrimary,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            title = "RUNNING METRICS",
            value = activeTournaments.toString(),
            icon = Icons.Default.PlayArrow,
            color = TitanAccent,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            title = "TITAN COFFER",
            value = "$${(prizePoolSum / 1000).toInt()}K",
            icon = Icons.Default.Star,
            color = TitanSecondary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = TitanSurface),
        border = BorderStroke(1.dp, TitanBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = TitanTextMuted,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color.copy(alpha = 0.8f),
                    modifier = Modifier.size(14.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun TeamSelectionCard(
    team: Team,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val borderColor = if (isSelected) TitanPrimary else TitanBorder
    val surfaceColor = if (isSelected) TitanPrimary.copy(alpha = 0.08f) else TitanSurface

    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onSelect)
            .testTag("team_card_${team.id}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(TitanPrimary.copy(alpha = 0.15f))
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = team.tag,
                        color = TitanPrimary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = team.game,
                    color = TitanTextMuted,
                    fontSize = 9.sp
                )
            }

            Text(
                text = team.name,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = TitanAccent,
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${team.wins}W - ${team.losses}L",
                    color = TitanTextMuted,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun PlayersRosterSection(
    team: Team?,
    roster: List<Player>
) {
    if (team == null) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = TitanSurface),
        border = BorderStroke(1.dp, TitanBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ACTIVE ROSTER",
                        color = TitanPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = team.name.uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(TitanAccent.copy(alpha = 0.1f))
                        .border(1.dp, TitanAccent.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${roster.size} ASSETS",
                        color = TitanAccent,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (roster.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("NO ASSETS ATTACHED", color = TitanTextMuted, fontSize = 12.sp)
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    roster.forEach { player ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(TitanBackground.copy(alpha = 0.5f))
                                .border(1.dp, TitanBorder, RoundedCornerShape(6.dp))
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(TitanSurface)
                                        .border(1.dp, TitanBorder, RoundedCornerShape(6.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (player.isCaptain) Icons.Default.Star else Icons.Default.Person,
                                        contentDescription = null,
                                        tint = if (player.isCaptain) TitanAccent else TitanPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = player.gamerTag,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                        if (player.isCaptain) {
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "IGL",
                                                color = TitanAccent,
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier
                                                    .background(TitanAccent.copy(alpha = 0.15f))
                                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = player.realName,
                                        color = TitanTextMuted,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = player.role,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = player.nationality,
                                    color = TitanTextMuted,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TournamentCard(tournament: Tournament) {
    val statusColor = when (tournament.status) {
        "Live" -> TitanAccent
        "Completed" -> TitanTextMuted
        else -> TitanPrimary
    }

    val statusBg = statusColor.copy(alpha = 0.15f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = TitanSurface),
        border = BorderStroke(1.dp, TitanBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(statusBg)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = tournament.status.uppercase(),
                            color = statusColor,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = tournament.game,
                        color = TitanTextMuted,
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = tournament.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${(tournament.prizePool / 1000).toInt()}K POOL",
                    color = TitanAccent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = if (tournament.status == "Upcoming") "Starts ${tournament.startDate}" else "${tournament.matchesPlayed} Matches",
                    color = TitanTextMuted,
                    fontSize = 9.sp
                )
            }
        }
    }
}
