package com.example.ui.screens.forum

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdMobManager
import com.example.data.local.entity.ForumCommentEntity
import com.example.data.local.entity.ForumPostEntity
import com.example.ui.components.AdMobBanner
import com.example.ui.components.AdMobNativeAd
import com.example.ui.components.AppAsyncImage
import com.example.ui.theme.CarriereBlue
import com.example.ui.theme.CarriereDeepBlue
import com.example.ui.theme.CarriereEmerald
import com.example.ui.theme.SkyBlue100
import com.example.ui.theme.SkyBlue50
import com.example.util.AppLanguage
import com.example.util.Localization

val forumCategories = listOf(
    "Toutes",
    "Négociation",
    "Retours d'Entretiens",
    "Conseils CV",
    "Reconversion"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(
    posts: List<ForumPostEntity>,
    selectedPost: ForumPostEntity?,
    comments: List<ForumCommentEntity>,
    language: AppLanguage,
    adMobManager: AdMobManager,
    onSelectPost: (ForumPostEntity?) -> Unit,
    onUpvotePost: (String) -> Unit,
    onCreatePost: (String, String, String, String?, String?) -> Unit,
    onAddComment: (String, String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Toutes") }
    var showCreateDialog by remember { mutableStateOf(false) }

    val filteredPosts = if (selectedCategory == "Toutes") {
        posts
    } else {
        posts.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = CarriereBlue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nouveau Sujet")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 80.dp)
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
                                imageVector = Icons.Default.Forum,
                                contentDescription = null,
                                tint = CarriereBlue,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = Localization.t("forum_title", language),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = Localization.t("forum_subtitle", language),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Category Filter Chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(forumCategories) { cat ->
                            FilterChip(
                                selected = selectedCategory == cat,
                                onClick = { selectedCategory = cat },
                                label = { Text(cat, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = CarriereBlue,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Posts List with Native Ad integration
            items(filteredPosts.withIndex().toList()) { (index, post) ->
                ForumPostCard(
                    post = post,
                    language = language,
                    onUpvote = { onUpvotePost(post.id) },
                    onClick = { onSelectPost(post) }
                )

                // Insert Native Ad after first post
                if (index == 0) {
                    AdMobNativeAd(adMobManager = adMobManager)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                AdMobBanner(adMobManager = adMobManager)
            }
        }
    }

    // Modal Sheet for Discussion Comments
    if (selectedPost != null) {
        ModalBottomSheet(
            onDismissRequest = { onSelectPost(null) },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            PostCommentsSheet(
                post = selectedPost,
                comments = comments,
                language = language,
                onAddComment = { text ->
                    onAddComment(selectedPost.id, text)
                },
                onClose = { onSelectPost(null) }
            )
        }
    }

    // Create New Topic Dialog
    if (showCreateDialog) {
        CreateTopicDialog(
            language = language,
            onDismiss = { showCreateDialog = false },
            onConfirm = { title, cat, content, imgUrl, imgB64 ->
                onCreatePost(title, cat, content, imgUrl, imgB64)
                showCreateDialog = false
            }
        )
    }
}

@Composable
fun ForumPostCard(
    post: ForumPostEntity,
    language: AppLanguage,
    onUpvote: () -> Unit,
    onClick: () -> Unit
) {
    val postImageModel: Any? = post.imageBase64 ?: post.imageUrl

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Category & Author Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(SkyBlue100),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = CarriereBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = post.author,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = post.authorRole,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CarriereBlue.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = post.category,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = CarriereBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Post Title
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Post Content Preview
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
            )

            // Post Photo if present
            if (postImageModel != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    AppAsyncImage(
                        model = postImageModel,
                        contentDescription = post.title,
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(12.dp),
                        contentScale = ContentScale.Crop,
                        enableZoomOnClick = true,
                        showSourceBadge = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Footer: Upvotes & Comments Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onUpvote() }
                        .background(SkyBlue50)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Upvote",
                        tint = CarriereBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${post.upvotes} ${Localization.t("forum_upvote", language)}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = CarriereBlue
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comments",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${post.commentsCount} ${Localization.t("forum_comments", language)}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun PostCommentsSheet(
    post: ForumPostEntity,
    comments: List<ForumCommentEntity>,
    language: AppLanguage,
    onAddComment: (String) -> Unit,
    onClose: () -> Unit
) {
    var replyText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Fermer")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = post.content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        val postSheetImage: Any? = post.imageBase64 ?: post.imageUrl
        if (postSheetImage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                AppAsyncImage(
                    model = postSheetImage,
                    contentDescription = post.title,
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(12.dp),
                    contentScale = ContentScale.Crop,
                    enableZoomOnClick = true,
                    showSourceBadge = true
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${comments.size} ${Localization.t("forum_comments", language)}",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
                .height(220.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (comments.isEmpty()) {
                item {
                    Text(
                        text = if (language == AppLanguage.FR) "Soyez le premier à répondre !" else "Be the first to reply!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(comments) { comment ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = SkyBlue50),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = comment.author,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = CarriereBlue
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = comment.content,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Input field for new comment
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = replyText,
                onValueChange = { replyText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(Localization.t("forum_reply", language), fontSize = 12.sp) },
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (replyText.isNotBlank()) {
                        onAddComment(replyText)
                        replyText = ""
                    }
                },
                enabled = replyText.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = CarriereBlue),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = "Envoyer", tint = Color.White)
            }
        }
    }
}

@Composable
fun CreateTopicDialog(
    language: AppLanguage,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String?, String?) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Négociation") }
    var content by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = Localization.t("forum_new_post", language),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Titre du sujet") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Text("Catégorie :", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(listOf("Négociation", "Retours d'Entretiens", "Conseils CV", "Reconversion")) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 11.sp) }
                        )
                    }
                }

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Votre question ou partage d'expérience") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Lien photo optionnel (URL)") },
                    placeholder = { Text("https://...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(title, selectedCategory, content, imageUrl.ifBlank { null }, null) },
                enabled = title.isNotBlank() && content.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = CarriereBlue)
            ) {
                Text("Publier (+40 XP)")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
