package com.example.data

data class FaqItem(
    val id: String,
    val questionFr: String,
    val questionEn: String,
    val answerFr: String,
    val answerEn: String,
    val category: String
)

object FaqCatalog {
    val items = listOf(
        FaqItem(
            id = "faq_1",
            questionFr = "Comment réussir un entretien vidéo ou téléphonique ?",
            questionEn = "How to succeed in a video or phone interview?",
            answerFr = "Assurez-vous d'avoir une connexion stable, un cadre neutre et bien éclairé de face. Regardez la webcam (et non l'écran) pour créer un contact visuel. Ayez sous les yeux la fiche de poste et vos notes clés en mots-clés simples.",
            answerEn = "Ensure a stable connection, good frontal lighting, and a quiet background. Look at the camera to maintain visual engagement. Keep brief keyword notes and the job description nearby.",
            category = "Entretiens"
        ),
        FaqItem(
            id = "faq_2",
            questionFr = "Qu'est-ce qu'un ATS et comment adapter mon CV ?",
            questionEn = "What is an ATS and how should I adapt my resume?",
            answerFr = "L'ATS (Applicant Tracking System) est le logiciel de tri automatique utilisé par 90% des grandes entreprises. Pour franchir ce filtre : évitez les colonnes complexes ou images contenant du texte, utilisez des polices standard et intégrez les mots-clés exacts de l'offre d'emploi.",
            answerEn = "ATS is an automated filtering software used by large employers. To pass it: avoid complex tables or image-embedded text, use clean headings, and match keywords directly from the job posting.",
            category = "CV & Candidature"
        ),
        FaqItem(
            id = "faq_3",
            questionFr = "Comment négocier ses prétentions salariales ?",
            questionEn = "How should I negotiate my salary expectations?",
            answerFr = "Renseignez-vous toujours en amont sur les grilles du secteur. Formulez une fourchette (ex: 45K€ - 50K€) en justifiant votre valeur ajoutée par des réalisations chiffrées passées. Prenez aussi en compte le package complet (variable, télétravail, tickets restaurant, RTT).",
            answerEn = "Research market rates beforehand. Provide a realistic range (e.g., €45k - €50k) supported by concrete metrics from past achievements. Factor in bonuses, remote allowances, and benefits.",
            category = "Rémunération"
        ),
        FaqItem(
            id = "faq_4",
            questionFr = "Comment fonctionne le mode hors ligne dans l'application ?",
            questionEn = "How does offline mode work in this app?",
            answerFr = "Grâce à notre architecture de persistance locale Room (SQLite), l'ensemble du catalogue d'offres, des modèles de CV, des fiches de questions et de vos candidatures est mis en cache sur votre appareil. Vous pouvez réviser et vous entraîner même en avion ou dans le métro.",
            answerEn = "Thanks to the local Room database (SQLite), all job listings, CV templates, interview guides, and your applications are cached locally on your device for seamless offline use anywhere.",
            category = "Application"
        ),
        FaqItem(
            id = "faq_5",
            questionFr = "À quoi servent les publicités AdMob et sont-elles intrusives ?",
            questionEn = "What is the purpose of AdMob ads and are they intrusive?",
            answerFr = "Les annonces Google AdMob (bannières, natives et interstitiels) financent les serveurs et le contenu de l'application pour qu'elle reste 100% gratuite. Nous appliquons un délai anti-spam strict de 40 secondes entre chaque annonce pour protéger votre concentration.",
            answerEn = "AdMob ads fund the app and content so it remains 100% free for all candidates. We enforce a strict 40-second cooldown between interstitials to respect user experience.",
            category = "Monétisation"
        ),
        FaqItem(
            id = "faq_6",
            questionFr = "Quels sont les avantages de la méthode STAR ?",
            questionEn = "What are the benefits of the STAR method?",
            answerFr = "La méthode STAR (Situation, Tâche, Action, Résultat) évite les réponses vagues ou trop théoriques. Elle démontre par des preuves concrètes et mesurables comment vous avez surmonté des défis professionnels réels.",
            answerEn = "The STAR method (Situation, Task, Action, Result) prevents vague answers. It provides recruiters with concrete, metric-backed proof of how you solved real problems.",
            category = "Entretiens"
        )
    )
}
