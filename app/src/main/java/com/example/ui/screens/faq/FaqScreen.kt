package com.example.ui.screens.faq

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdMobManager
import com.example.data.FaqCatalog
import com.example.data.FaqItem
import com.example.ui.components.AdMobBanner
import com.example.ui.components.AdMobNativeAd
import com.example.ui.theme.CarriereBlue
import com.example.ui.theme.CarriereDeepBlue
import com.example.ui.theme.SkyBlue100
import com.example.ui.theme.SkyBlue50
import com.example.util.AppLanguage
import com.example.util.Localization

@Composable
fun FaqScreen(
    language: AppLanguage,
    adMobManager: AdMobManager
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Tous") }
    val expandedIds = remember { mutableStateOf(setOf("faq_1")) }

    val allCategories = listOf("Tous", "Entretiens", "CV & Candidature", "Rémunération", "Application", "Monétisation")

    val filteredFaq = FaqCatalog.items.filter { item ->
        val q = searchQuery.trim().lowercase()
        val question = if (language == AppLanguage.FR) item.questionFr else item.questionEn
        val answer = if (language == AppLanguage.FR) item.answerFr else item.answerEn

        val matchesQuery = q.isEmpty() ||
                question.lowercase().contains(q) ||
                answer.lowercase().contains(q)

        val matchesCategory = selectedCategory == "Tous" || item.category == selectedCategory

        matchesQuery && matchesCategory
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SkyBlue100),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = null,
                            tint = CarriereBlue,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = Localization.t("faq_title", language),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = Localization.t("faq_subtitle", language),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Search Box
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(Localization.t("faq_search", language), fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = CarriereBlue)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Category Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(allCategories) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CarriereBlue,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // FAQ Items
        items(filteredFaq.withIndex().toList()) { (index, item) ->
            val isExpanded = expandedIds.value.contains(item.id)
            val question = if (language == AppLanguage.FR) item.questionFr else item.questionEn
            val answer = if (language == AppLanguage.FR) item.answerFr else item.answerEn

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp)
                    .clickable {
                        expandedIds.value = if (isExpanded) {
                            expandedIds.value - item.id
                        } else {
                            expandedIds.value + item.id
                        }
                    },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isExpanded) SkyBlue50 else MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(CarriereBlue.copy(alpha = 0.1f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = item.category,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CarriereBlue
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = question,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) "Réduire" else "Développer",
                            tint = CarriereBlue
                        )
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Column {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = answer,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 21.sp
                            )
                        }
                    }
                }
            }

            // Insert Native Ad in middle of FAQ
            if (index == 2) {
                AdMobNativeAd(adMobManager = adMobManager)
            }
        }

        // Bottom Banner
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AdMobBanner(adMobManager = adMobManager)
        }
    }
}
