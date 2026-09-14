package com.example.data.model

data class JobOffer(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val sector: String,
    val contractType: String, // CDI, CDD, Alternance, Freelance, Stage
    val salary: String,
    val remotePolicy: String, // Télétravail total, Hybride, Sur site
    val postedAgo: String,
    val description: String,
    val missions: List<String>,
    val requirements: List<String>,
    val perks: List<String>,
    val isFeatured: Boolean = false,
    val imageUrl: String? = null,
    val companyLogoUrl: String? = null
)

data class InterviewQuestion(
    val id: String,
    val question: String,
    val category: String, // Classique, Piège, Compétences, Clôture
    val difficulty: String, // Débutant, Intermédiaire, Avancé
    val recruiterIntent: String,
    val recommendedAnswer: String,
    val mistakesToAvoid: String,
    val starExample: String? = null
)

data class CvSection(
    val title: String,
    val content: String
)

data class CvTemplate(
    val id: String,
    val title: String,
    val sector: String,
    val experienceLevel: String, // Junior, Confirmé, Senior, Reconversion
    val description: String,
    val atsScore: Int,
    val keyStrengths: List<String>,
    val atsTips: List<String>,
    val summaryTemplate: String,
    val experienceTemplate: String,
    val skillsTemplate: List<String>,
    val educationTemplate: String,
    val suggestedColors: List<String>
)
