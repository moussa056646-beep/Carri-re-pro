package com.example.util

enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    FR("fr", "Français", "🇫🇷"),
    EN("en", "English", "🇬🇧")
}

object Localization {
    fun t(key: String, language: AppLanguage): String {
        return when (language) {
            AppLanguage.FR -> frenchStrings[key] ?: key
            AppLanguage.EN -> englishStrings[key] ?: key
        }
    }

    private val frenchStrings = mapOf(
        // Tabs
        "tab_dashboard" to "Tableau de Bord",
        "tab_jobs" to "Emplois",
        "tab_interviews" to "Entretiens",
        "tab_cv" to "Modèles CV",
        "tab_forum" to "Forum",
        "tab_favorites" to "Mon Espace",
        "tab_faq" to "FAQ",

        // Dashboard
        "dash_title" to "Tableau de Bord Carrière",
        "dash_welcome" to "Bonjour Alexandre, prêt à booster votre carrière ?",
        "dash_readiness" to "Score de Préparation Embauche",
        "dash_streak" to "Jours consécutifs",
        "dash_level" to "Niveau",
        "dash_xp" to "Points d'expérience",
        "dash_daily_challenge" to "Défi du jour : Valider 1 pitch STAR",
        "dash_start_challenge" to "Commencer le défi (+50 XP)",
        "dash_quick_actions" to "Actions Rapides",
        "dash_act_star" to "Simulateur STAR",
        "dash_act_jobs" to "Explorer les Jobs",
        "dash_act_cv" to "Exemples de CV",
        "dash_act_forum" to "Entraide Forum",
        "dash_gamification_title" to "Vos Badges & Succès",
        "dash_push_title" to "Notifications Push de Coaching",
        "dash_test_push" to "Envoyer une alerte de coaching test",
        "dash_offline_mode" to "Mode Hors Ligne",
        "dash_offline_desc" to "Accès 100% garanti à toutes vos ressources sauvegardées sans connexion.",

        // Status
        "status_online" to "En Ligne",
        "status_offline" to "Hors Ligne (Cache Actif)",

        // Jobs
        "jobs_search_placeholder" to "Rechercher un poste, une entreprise, un mot-clé...",
        "jobs_all_sectors" to "Tous les secteurs",
        "jobs_apply" to "Postuler à cette opportunité",
        "jobs_apply_success" to "Candidature enregistrée avec succès !",
        "jobs_applied_on" to "Postulé le",

        // Interviews
        "interview_title" to "Guide Complet d'Entretiens",
        "interview_star" to "Méthode STAR",
        "interview_flashcards" to "Flashcards Express",
        "interview_checklist" to "Checklist J-7 à J-0",

        // CV
        "cv_title" to "Exemples de CV Professionnels",
        "cv_copy" to "Copier le texte du modèle de CV",

        // Forum
        "forum_title" to "Forum & Communauté Carrière",
        "forum_subtitle" to "Échangez entre candidats, retours d'expériences et astuces de recrutement.",
        "forum_new_post" to "Poser une question / Partager un sujet",
        "forum_upvote" to "Utile",
        "forum_comments" to "Réponses",
        "forum_reply" to "Répondre à cette discussion",

        // FAQ
        "faq_title" to "Foire Aux Questions (FAQ)",
        "faq_subtitle" to "Toutes les réponses pour maximiser votre réussite professionnelle.",
        "faq_search" to "Rechercher une réponse...",

        // Language
        "language_select" to "Langue de l'interface"
    )

    private val englishStrings = mapOf(
        // Tabs
        "tab_dashboard" to "Dashboard",
        "tab_jobs" to "Jobs",
        "tab_interviews" to "Interviews",
        "tab_cv" to "CV Templates",
        "tab_forum" to "Forum",
        "tab_favorites" to "My Space",
        "tab_faq" to "FAQ",

        // Dashboard
        "dash_title" to "Career Dashboard",
        "dash_welcome" to "Hello Alexandre, ready to boost your career?",
        "dash_readiness" to "Hiring Readiness Score",
        "dash_streak" to "Day Streak",
        "dash_level" to "Level",
        "dash_xp" to "Experience Points",
        "dash_daily_challenge" to "Daily Challenge: Master 1 STAR pitch",
        "dash_start_challenge" to "Start Challenge (+50 XP)",
        "dash_quick_actions" to "Quick Actions",
        "dash_act_star" to "STAR Simulator",
        "dash_act_jobs" to "Browse Jobs",
        "dash_act_cv" to "CV Templates",
        "dash_act_forum" to "Community Forum",
        "dash_gamification_title" to "Your Badges & Achievements",
        "dash_push_title" to "Career Push Notifications",
        "dash_test_push" to "Send a test coaching alert",
        "dash_offline_mode" to "Offline Mode",
        "dash_offline_desc" to "100% offline access to all cached jobs and interview guides.",

        // Status
        "status_online" to "Online",
        "status_offline" to "Offline (Local Cache)",

        // Jobs
        "jobs_search_placeholder" to "Search job titles, companies, keywords...",
        "jobs_all_sectors" to "All sectors",
        "jobs_apply" to "Apply for this opportunity",
        "jobs_apply_success" to "Application submitted successfully!",
        "jobs_applied_on" to "Applied on",

        // Interviews
        "interview_title" to "Interview Preparation Guide",
        "interview_star" to "STAR Method",
        "interview_flashcards" to "Express Flashcards",
        "interview_checklist" to "D-7 to Day-D Checklist",

        // CV
        "cv_title" to "Professional CV Templates",
        "cv_copy" to "Copy CV Template Text",

        // Forum
        "forum_title" to "Career Community & Forum",
        "forum_subtitle" to "Discuss with other job seekers, share interview feedback & salary tips.",
        "forum_new_post" to "Ask a Question / New Topic",
        "forum_upvote" to "Helpful",
        "forum_comments" to "Replies",
        "forum_reply" to "Reply to this discussion",

        // FAQ
        "faq_title" to "Frequently Asked Questions (FAQ)",
        "faq_subtitle" to "Everything you need to know to excel in your job search.",
        "faq_search" to "Search a question or answer...",

        // Language
        "language_select" to "App Language"
    )
}
