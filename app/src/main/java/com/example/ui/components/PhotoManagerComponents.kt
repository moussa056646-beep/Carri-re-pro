package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.AppPhotoEntity
import com.example.ui.theme.CarriereBlue
import com.example.ui.theme.CarriereDeepBlue
import com.example.ui.theme.CarriereEmerald
import com.example.ui.theme.Slate700

@Composable
fun PhotoGallerySection(
    photos: List<AppPhotoEntity>,
    onAddPhotoUrl: (title: String, category: String, url: String, desc: String) -> Unit,
    onAddPhotoDatabase: (title: String, category: String, base64: String, desc: String) -> Unit,
    onAddPresetDatabase: (title: String, category: String, colorHex: Int, line1: String, line2: String, desc: String) -> Unit,
    onDeletePhoto: (id: String) -> Unit,
    onSetAsAvatar: (photo: AppPhotoEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Toutes") }
    var selectedPhotoForViewer by remember { mutableStateOf<AppPhotoEntity?>(null) }

    val filteredPhotos = remember(photos, selectedFilter) {
        when (selectedFilter) {
            "Toutes" -> photos
            "En ligne (URL)" -> photos.filter { it.sourceType == "HOSTED_URL" }
            "Base SQLite" -> photos.filter { it.sourceType == "DATABASE_STORAGE" }
            else -> photos.filter { it.category == selectedFilter }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(CarriereBlue.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = null,
                            tint = CarriereBlue,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Photos & Documents",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CarriereBlue.copy(alpha = 0.15f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${photos.size}",
                                    color = CarriereBlue,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Hébergées en ligne ou en base SQLite",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = { showAddDialog = true },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CarriereBlue)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Ajouter", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Filter Chips
            val filterOptions = listOf("Toutes", "En ligne (URL)", "Base SQLite", "Profil & CV", "Entreprise", "Attestation & Diplôme")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 2.dp)
            ) {
                items(filterOptions) { filter ->
                    val isSelected = selectedFilter == filter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        label = { Text(text = filter, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CarriereBlue,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Photo Cards Horizontal Row
            if (filteredPhotos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Aucune photo dans cette catégorie",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 2.dp)
                ) {
                    items(filteredPhotos, key = { it.id }) { photo ->
                        PhotoCardItem(
                            photo = photo,
                            onClick = { selectedPhotoForViewer = photo },
                            onDelete = { onDeletePhoto(photo.id) },
                            onSetAsAvatar = { onSetAsAvatar(photo) }
                        )
                    }
                }
            }
        }
    }

    // Add Photo Dialog
    if (showAddDialog) {
        AddPhotoDialog(
            onDismiss = { showAddDialog = false },
            onAddUrl = { title, category, url, desc ->
                onAddPhotoUrl(title, category, url, desc)
                showAddDialog = false
            },
            onAddDatabase = { title, category, base64, desc ->
                onAddPhotoDatabase(title, category, base64, desc)
                showAddDialog = false
            },
            onAddPreset = { title, category, colorHex, line1, line2, desc ->
                onAddPresetDatabase(title, category, colorHex, line1, line2, desc)
                showAddDialog = false
            }
        )
    }

    // Fullscreen viewer
    selectedPhotoForViewer?.let { photo ->
        val model: Any? = photo.imageBase64 ?: photo.imageUrl
        if (model != null) {
            FullscreenPhotoViewerDialog(
                model = model,
                title = photo.title,
                category = photo.category,
                description = photo.description,
                isDatabaseStored = photo.sourceType == "DATABASE_STORAGE",
                onDismiss = { selectedPhotoForViewer = null }
            )
        }
    }
}

@Composable
fun PhotoCardItem(
    photo: AppPhotoEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onSetAsAvatar: () -> Unit
) {
    val model: Any? = photo.imageBase64 ?: photo.imageUrl
    val isDatabase = photo.sourceType == "DATABASE_STORAGE"

    Card(
        modifier = Modifier
            .width(170.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Column {
            // Image with badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
            ) {
                AppAsyncImage(
                    model = model,
                    contentDescription = photo.title,
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    showSourceBadge = true
                )

                // Top source pill
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (isDatabase) CarriereEmerald.copy(alpha = 0.9f)
                            else CarriereBlue.copy(alpha = 0.9f)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (isDatabase) "💾 Base SQLite" else "🌐 En ligne",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Text info & actions
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = photo.category,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = photo.title,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onSetAsAvatar,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Définir comme avatar",
                            tint = CarriereBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Supprimer",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Dialogue complet permettant d'ajouter une image via lien URL hébergé OU
 * directement encodée en base de données SQLite.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPhotoDialog(
    onDismiss: () -> Unit,
    onAddUrl: (title: String, category: String, url: String, desc: String) -> Unit,
    onAddDatabase: (title: String, category: String, base64: String, desc: String) -> Unit,
    onAddPreset: (title: String, category: String, colorHex: Int, line1: String, line2: String, desc: String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = URL, 1 = Base SQLite

    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Profil & CV") }
    var description by remember { mutableStateOf("") }

    // Tab 0 : URL State
    var urlInput by remember { mutableStateOf("") }

    // Tab 1 : Database Storage State
    var selectedPresetIndex by remember { mutableIntStateOf(0) }
    var customBase64Input by remember { mutableStateOf("") }
    var usePreset by remember { mutableStateOf(true) }

    val categories = listOf("Profil & CV", "Entreprise", "Attestation & Diplôme", "Conseils & Schémas")

    val urlPresets = listOf(
        Triple("Espace Bureaux Tech", "Entreprise", "https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=800&auto=format&fit=crop&q=80"),
        Triple("Clinique Médicale Pro", "Entreprise", "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=800&auto=format&fit=crop&q=80"),
        Triple("Quartier Financier", "Entreprise", "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=800&auto=format&fit=crop&q=80"),
        Triple("Schéma Méthode STAR", "Conseils & Schémas", "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=800&auto=format&fit=crop&q=80")
    )

    val dbPresets = listOf(
        PresetItem("Badge Certifié 2026", "Attestation & Diplôme", 0xFF0D9488.toInt(), "CERTIF", "OFFICIEL", "Badge vérifié en base SQLite"),
        PresetItem("Avatar Candidat Alexandre", "Profil & CV", 0xFF1E3A8A.toInt(), "PROFIL", "ALEXANDRE", "Avatar stocké directement en table Room"),
        PresetItem("Attestation RH Reçue", "Attestation & Diplôme", 0xFFD97706.toInt(), "ACCORD", "EMBAUCHE", "Preuve d'offre d'emploi"),
        PresetItem("Fiche Synthèse Entretien", "Conseils & Schémas", 0xFF6366F1.toInt(), "MÉTHODE", "STAR 100%", "Fiche mémotechnique offline")
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Ajouter une image",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Choisissez entre URL hébergée ou stockage SQLite",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Mode Selector Tabs
                SecondaryTabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CloudDone, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Lien hébergé (URL)", fontSize = 12.sp)
                            }
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Storage, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Base SQLite", fontSize = 12.sp)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Titre de la photo") },
                    placeholder = { Text("ex. Locaux Entreprise ou Diplôme") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category Chips
                Text("Catégorie :", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = category == cat,
                            onClick = { category = cat },
                            label = { Text(cat, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CarriereBlue,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (selectedTab == 0) {
                    // --- TAB 0: HOSTED URL ---
                    OutlinedTextField(
                        value = urlInput,
                        onValueChange = { urlInput = it },
                        label = { Text("URL de l'image (HTTP / HTTPS)") },
                        placeholder = { Text("https://images.unsplash.com/...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Ou sélectionnez une image préconfigurée :", fontSize = 11.sp, color = Slate700)
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        urlPresets.forEach { preset ->
                            OutlinedButton(
                                onClick = {
                                    title = preset.first
                                    category = preset.second
                                    urlInput = preset.third
                                    description = "Image hébergée via CDN Unsplash"
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(preset.first, fontSize = 11.sp)
                                    Icon(Icons.Default.Link, contentDescription = null, modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                    }

                    // Live Preview if URL is valid
                    if (urlInput.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Aperçu du chargement :", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            AppAsyncImage(
                                model = urlInput,
                                contentDescription = "Aperçu",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                } else {
                    // --- TAB 1: DIRECT DATABASE STORAGE ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(CarriereEmerald.copy(alpha = 0.08f))
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Storage,
                                contentDescription = null,
                                tint = CarriereEmerald,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "L'image sera convertie en données binaires Base64 et stockée directement dans la base de données Room (table SQLite 'app_photos') pour une disponibilité 100% hors-ligne.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Sélectionnez le visuel à générer et injecter en base :", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        dbPresets.forEachIndexed { index, preset ->
                            val isChosen = selectedPresetIndex == index && usePreset
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedPresetIndex = index
                                        usePreset = true
                                        title = preset.name
                                        category = preset.category
                                        description = preset.desc
                                    },
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isChosen) CarriereEmerald.copy(alpha = 0.15f)
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                ),
                                border = if (isChosen) androidx.compose.foundation.BorderStroke(1.5.dp, CarriereEmerald) else null
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(preset.colorHex)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = preset.line1.take(3),
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(preset.name, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text(preset.desc, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    if (isChosen) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = CarriereEmerald,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Optional manual Base64 string
                    OutlinedTextField(
                        value = customBase64Input,
                        onValueChange = {
                            customBase64Input = it
                            if (it.isNotBlank()) usePreset = false
                        },
                        label = { Text("Ou collez une chaîne Base64 personnalisée") },
                        placeholder = { Text("data:image/png;base64,iVBORw...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 3
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description ou notes") },
                    placeholder = { Text("ex. Pris lors du forum de recrutement") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedTab == 0) {
                        val finalUrl = urlInput.ifBlank {
                            "https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=800&auto=format&fit=crop&q=80"
                        }
                        onAddUrl(
                            title.ifBlank { "Photo hébergée" },
                            category,
                            finalUrl,
                            description
                        )
                    } else {
                        if (usePreset) {
                            val preset = dbPresets[selectedPresetIndex]
                            onAddPreset(
                                title.ifBlank { preset.name },
                                category,
                                preset.colorHex,
                                preset.line1,
                                preset.line2,
                                description.ifBlank { preset.desc }
                            )
                        } else {
                            onAddDatabase(
                                title.ifBlank { "Image SQLite" },
                                category,
                                customBase64Input,
                                description
                            )
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTab == 1) CarriereEmerald else CarriereBlue
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(if (selectedTab == 1) "Enregistrer en SQLite" else "Ajouter l'URL")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

data class PresetItem(
    val name: String,
    val category: String,
    val colorHex: Int,
    val line1: String,
    val line2: String,
    val desc: String
)
