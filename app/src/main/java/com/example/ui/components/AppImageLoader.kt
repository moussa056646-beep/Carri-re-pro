package com.example.ui.components

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.ui.theme.CarriereBlue
import com.example.ui.theme.CarriereDeepBlue
import com.example.ui.theme.CarriereEmerald

object ImageDecoderUtils {
    fun isBase64String(str: String): Boolean {
        return str.startsWith("data:image") || str.startsWith("/9j/") || str.startsWith("iVBORw0KGgo") ||
                (str.length > 50 && !str.startsWith("http://") && !str.startsWith("https://") && !str.startsWith("android.resource://"))
    }

    fun decodeBase64ToByteArray(base64Str: String): ByteArray? {
        return try {
            val clean = if (base64Str.contains(",")) {
                base64Str.substringAfter(",")
            } else {
                base64Str.trim()
            }
            Base64.decode(clean, Base64.DEFAULT)
        } catch (_: Throwable) {
            null
        }
    }
}

/**
 * Système universel de chargement d'images pour Carrière Pro.
 * Prend en charge :
 * - Les URLs hébergées en ligne (HTTP/HTTPS) avec mise en cache Coil
 * - Les images intégrées directement dans la base de données SQLite (Base64 ou ByteArray)
 * - Les ressources locales Android
 */
@Composable
fun AppAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = RoundedCornerShape(12.dp),
    fallbackIcon: ImageVector = Icons.Default.Image,
    fallbackText: String? = null,
    showSourceBadge: Boolean = false,
    enableZoomOnClick: Boolean = false,
    viewerTitle: String? = null,
    viewerDescription: String? = null,
    viewerCategory: String? = null,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var showViewerDialog by remember { mutableStateOf(false) }

    // Détection de la source
    val (resolvedData, isDatabaseSource) = remember(model) {
        when (model) {
            is String -> {
                if (ImageDecoderUtils.isBase64String(model)) {
                    val bytes = ImageDecoderUtils.decodeBase64ToByteArray(model)
                    (bytes ?: model) to true
                } else {
                    model to false
                }
            }
            is ByteArray -> model to true
            else -> model to false
        }
    }

    val imageRequest = remember(resolvedData, context) {
        ImageRequest.Builder(context)
            .data(resolvedData)
            .crossfade(300)
            .build()
    }

    val finalClickHandler: (() -> Unit)? = when {
        onClick != null -> onClick
        enableZoomOnClick && model != null -> {
            { showViewerDialog = true }
        }
        else -> null
    }

    Box(
        modifier = modifier
            .clip(shape)
            .then(
                if (finalClickHandler != null) {
                    Modifier.clickable(onClick = finalClickHandler)
                } else Modifier
            )
    ) {
        if (model == null || (model is String && model.isBlank())) {
            // Placeholder si aucune image fournie
            ImageFallbackBox(
                icon = fallbackIcon,
                text = fallbackText,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            SubcomposeAsyncImage(
                model = imageRequest,
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    ImageShimmerLoading(modifier = Modifier.fillMaxSize())
                },
                error = {
                    ImageFallbackBox(
                        icon = Icons.Default.BrokenImage,
                        text = "Image indisponible",
                        modifier = Modifier.fillMaxSize()
                    )
                },
                success = {
                    SubcomposeAsyncImageContent()
                }
            )
        }

        // Badge discret indiquant si l'image provient d'un lien hébergé ou de la base SQLite
        if (showSourceBadge && model != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Black.copy(alpha = 0.65f))
                    .padding(horizontal = 5.dp, vertical = 2.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isDatabaseSource) Icons.Default.Storage else Icons.Default.CloudDone,
                        contentDescription = null,
                        tint = if (isDatabaseSource) CarriereEmerald else Color(0xFF60A5FA),
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = if (isDatabaseSource) "DB" else "URL",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Visionneuse plein écran si activée
    if (showViewerDialog && model != null) {
        FullscreenPhotoViewerDialog(
            model = model,
            title = viewerTitle ?: contentDescription ?: "Aperçu de la photo",
            category = viewerCategory ?: (if (isDatabaseSource) "Base de données SQLite" else "Image hébergée en ligne"),
            description = viewerDescription ?: "",
            isDatabaseStored = isDatabaseSource,
            onDismiss = { showViewerDialog = false }
        )
    }
}

@Composable
fun ImageShimmerLoading(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmerAlpha"
    )

    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha * 0.6f),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            strokeWidth = 2.dp,
            color = CarriereBlue.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun ImageFallbackBox(
    icon: ImageVector,
    text: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
            if (!text.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = text,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Dialogue de visualisation plein écran d'une photo avec inspection de la source (URL ou base SQLite)
 */
@Composable
fun FullscreenPhotoViewerDialog(
    model: Any,
    title: String,
    category: String,
    description: String,
    isDatabaseStored: Boolean,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.94f)),
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (isDatabaseStored) CarriereEmerald.copy(alpha = 0.25f)
                                        else CarriereBlue.copy(alpha = 0.25f)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isDatabaseStored) Icons.Default.Storage else Icons.Default.CloudDone,
                                        contentDescription = null,
                                        tint = if (isDatabaseStored) CarriereEmerald else Color(0xFF93C5FD),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isDatabaseStored) "Stocké en Base SQLite" else "Lien hébergé en ligne",
                                        color = if (isDatabaseStored) CarriereEmerald else Color(0xFF93C5FD),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = category,
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = title,
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fermer",
                            tint = Color.White
                        )
                    }
                }

                // Photo Display Container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF111827)),
                    contentAlignment = Alignment.Center
                ) {
                    AppAsyncImage(
                        model = model,
                        contentDescription = title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        shape = RoundedCornerShape(16.dp),
                        enableZoomOnClick = false
                    )
                }

                // Footer Metadata Details
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1F2937).copy(alpha = 0.9f)
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        if (description.isNotBlank()) {
                            Text(
                                text = description,
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isDatabaseStored)
                                    "Format : Image encodée & persistée dans Room DB"
                                else
                                    "Format : URL Web chargée dynamiquement via Coil",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 11.sp
                            )
                            Text(
                                text = "Mise en cache : Active",
                                color = CarriereEmerald,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
