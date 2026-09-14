package com.example.data

import com.example.data.model.CvTemplate
import com.example.data.model.InterviewQuestion
import com.example.data.model.JobOffer

object CareerCatalog {

    val sectors = listOf(
        "Tous les secteurs",
        "Tech & Digital",
        "Santé & Médical",
        "Finance & Gestion",
        "Marketing & Vente",
        "Ingénierie & Industrie",
        "RH & Juridique"
    )

    val contractTypes = listOf("Tous", "CDI", "CDD", "Alternance", "Freelance", "Stage")

    val jobOffers: List<JobOffer> = listOf(
        JobOffer(
            id = "job-1",
            title = "Développeur Android Senior (Kotlin / Compose)",
            company = "NexTech Solutions",
            location = "Paris (75) / Hybride",
            sector = "Tech & Digital",
            contractType = "CDI",
            salary = "55 000 € - 68 000 € / an",
            remotePolicy = "Hybride (2j télétravail)",
            postedAgo = "Il y a 2 jours",
            description = "Rejoignez une scale-up en pleine expansion pour concevoir et faire évoluer notre application mobile native utilisée par plus de 500 000 utilisateurs quotidiens.",
            missions = listOf(
                "Développer de nouvelles fonctionnalités en Kotlin moderne et Jetpack Compose",
                "Concevoir une architecture modulaire et réactive (Clean Architecture, MVI/MVVM, Flow)",
                "Participer aux revues de code et encadrer des développeurs juniors",
                "Optimiser les performances et la fluidité de l'application"
            ),
            requirements = listOf(
                "Au moins 4 ans d'expérience en développement Android Kotlin",
                "Maîtrise éprouvée de Jetpack Compose, Coroutines et StateFlow",
                "Expérience en tests unitaires et intégration continue (CI/CD)",
                "Sensibilité forte pour l'UX/UI et les directives Material Design 3"
            ),
            perks = listOf("Tickets restaurant Swile 10€", "Mutuelle Alan prise en charge à 100%", "Budget équipement télétravail", "Prime d'intéressement annuelle"),
            isFeatured = true,
            imageUrl = "https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=800&auto=format&fit=crop&q=80",
            companyLogoUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=200&auto=format&fit=crop&q=80"
        ),
        JobOffer(
            id = "job-2",
            title = "Cadre de Santé / Coordonnateur de Soins",
            company = "Groupe Santé Harmonie",
            location = "Lyon (69)",
            sector = "Santé & Médical",
            contractType = "CDI",
            salary = "42 000 € - 52 000 € / an",
            remotePolicy = "Sur site",
            postedAgo = "Il y a 1 jour",
            description = "Sous la direction médicale, vous pilotez une équipe pluridisciplinaire de 25 soignants au sein d'un établissement moderne certifié Haute Qualité de Soins.",
            missions = listOf(
                "Organiser l'activité quotidienne et la continuité des soins",
                "Manager l'équipe soignante et coordonner les plannings",
                "Veiller au respect des protocoles d'hygiène et de qualité",
                "Accompagner les familles et animer les réunions d'équipe"
            ),
            requirements = listOf(
                "Diplôme d'État d'Infirmier + Diplôme de Cadre de Santé ou équivalent",
                "Capacités managériales et qualités d'écoute éprouvées",
                "Rigueur organisationnelle et sens des priorités"
            ),
            perks = listOf("Régime de prévoyance avantageux", "Crèche d'entreprise", "Formations professionnelles régulières"),
            isFeatured = true,
            imageUrl = "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=800&auto=format&fit=crop&q=80",
            companyLogoUrl = "https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?w=200&auto=format&fit=crop&q=80"
        ),
        JobOffer(
            id = "job-3",
            title = "Contrôleur de Gestion Opérationnel",
            company = "Axiom Finance & Conseil",
            location = "Nantes (44) / Télétravail partiel",
            sector = "Finance & Gestion",
            contractType = "CDI",
            salary = "45 000 € - 54 000 € / an",
            remotePolicy = "Hybride (3j télétravail)",
            postedAgo = "Il y a 3 jours",
            description = "Directement rattaché à la Direction Financière, vous accompagnez les directeurs de filiales dans le pilotage de la performance économique.",
            missions = listOf(
                "Élaborer les budgets prévisionnels et suivre les atterrissages mensuels",
                "Analyser les écarts de rentabilité et concevoir des tableaux de bord PowerBI",
                "Optimiser les coûts de revient et proposer des leviers d'amélioration",
                "Participer aux clôtures comptables en collaboration avec les équipes financières"
            ),
            requirements = listOf(
                "Master en Finance, Contrôle de Gestion ou DSCG",
                "3 à 5 ans d'expérience en contrôle de gestion industriel ou de services",
                "Excellente maîtrise d'Excel avancé et d'un outil BI (Power BI, Tableau)"
            ),
            perks = listOf("Participation et intéressement", "Accès salle de sport", "Forfait mobilités durables"),
            isFeatured = false,
            imageUrl = "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=800&auto=format&fit=crop&q=80",
            companyLogoUrl = "https://images.unsplash.com/photo-1554224155-8d04cb21cd6c?w=200&auto=format&fit=crop&q=80"
        ),
        JobOffer(
            id = "job-4",
            title = "Responsable Marketing Digital & Growth",
            company = "Aura Media Brand",
            location = "Bordeaux (33) / Hybride",
            sector = "Marketing & Vente",
            contractType = "CDI",
            salary = "48 000 € - 58 000 € / an",
            remotePolicy = "Hybride (2j télétravail)",
            postedAgo = "Il y a 4 jours",
            description = "Prenez en main la stratégie d'acquisition multi-canaux (SEO, SEA, Social Ads) pour propulser notre plateforme e-commerce en Europe.",
            missions = listOf(
                "Définir la stratégie d'acquisition payante (Google Ads, Meta Ads, TikTok)",
                "Optimiser le taux de conversion (CRO) et les parcours clients",
                "Gérer un budget média annuel de plus de 800 000 €",
                "Manager une équipe de 3 spécialistes acquisition et contenu"
            ),
            requirements = listOf(
                "Bac+5 Marketing/Communication ou école de commerce",
                "Expérience solide en pilotage de campagnes ROIstes à fort volume",
                "Esprit analytique poussé (Google Analytics 4, Mixpanel, Tag Manager)"
            ),
            perks = listOf("Prime sur objectifs trimestrielle", "Télétravail flexible", "PC Mac au choix"),
            isFeatured = true,
            imageUrl = "https://images.unsplash.com/photo-1542744173-8e7e53415bb0?w=800&auto=format&fit=crop&q=80",
            companyLogoUrl = "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=200&auto=format&fit=crop&q=80"
        ),
        JobOffer(
            id = "job-5",
            title = "Ingénieur Qualité & Amélioration Continue",
            company = "Altim Technologies",
            location = "Toulouse (31)",
            sector = "Ingénierie & Industrie",
            contractType = "CDI",
            salary = "44 000 € - 55 000 € / an",
            remotePolicy = "Sur site",
            postedAgo = "Il y a 5 jours",
            description = "Au cœur de notre unité de production aéronautique, vous garantissez la conformité des procédés et conduisez les chantiers Lean Six Sigma.",
            missions = listOf(
                "Piloter les audits internes et traiter les non-conformités (8D, 5 Pourquoi)",
                "Animer des chantiers d'amélioration continue (5S, Kaizen, SMED)",
                "Former les équipes de production aux standards qualité",
                "Interagir avec les clients et fournisseurs lors des audits de qualification"
            ),
            requirements = listOf(
                "Diplôme d'Ingénieur Généraliste, Mécanique ou Qualité",
                "Certification Lean Green Belt appréciée",
                "Anglais professionnel courant (contexte international)"
            ),
            perks = listOf("13ème mois garanti", "RTT avantageux (18j/an)", "Plan d'épargne entreprise abondé"),
            isFeatured = false,
            imageUrl = "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=800&auto=format&fit=crop&q=80",
            companyLogoUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=200&auto=format&fit=crop&q=80"
        ),
        JobOffer(
            id = "job-6",
            title = "Talent Acquisition Specialist (Tech & Sales)",
            company = "Elevate Partners",
            location = "Lille (59) / Télétravail total",
            sector = "RH & Juridique",
            contractType = "CDI",
            salary = "38 000 € - 46 000 € / an",
            remotePolicy = "Télétravail total",
            postedAgo = "Il y a 1 jour",
            description = "Vous êtes responsable du cycle complet de recrutement des profils clés (ingénieurs, commerciaux) dans un environnement dynamique et bienveillant.",
            missions = listOf(
                "Sourcer activement sur LinkedIn Recruiter et plateformes spécialisées",
                "Mener les entretiens de préqualification et les entretiens RH approfondis",
                "Développer la marque employeur lors d'événements et forums écoles",
                "Améliorer l'expérience candidat de la candidature jusqu'à l'onboarding"
            ),
            requirements = listOf(
                "2 ans d'expérience minimum en recrutement en cabinet ou entreprise tech",
                "Excellente communication orale et écrite",
                "Capacité d'organisation et sens du relationnel"
            ),
            perks = listOf("100% remote possible", "Indemnité mensuelle télétravail", "Budget formation annuel"),
            isFeatured = false,
            imageUrl = "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=800&auto=format&fit=crop&q=80",
            companyLogoUrl = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=200&auto=format&fit=crop&q=80"
        ),
        JobOffer(
            id = "job-7",
            title = "Data Analyst & Business Intelligence",
            company = "NovaMetrics",
            location = "Paris (75) / Hybride",
            sector = "Tech & Digital",
            contractType = "Alternance",
            salary = "1 400 € - 1 800 € / mois",
            remotePolicy = "Hybride (2j télétravail)",
            postedAgo = "Il y a 2 jours",
            description = "Rejoignez l'équipe Data pour transformer les données brutes en insights stratégiques actionnables par les équipes produit et direction.",
            missions = listOf(
                "Créer des requêtes SQL complexes et automatiser les pipelines de reporting",
                "Concevoir des dashboards interactifs sous Power BI et Tableau",
                "Mesurer l'impact des fonctionnalités produit par des analyses de cohortes"
            ),
            requirements = listOf(
                "Étudiant en Master Data / Informatique ou école d'ingénieurs",
                "Bonne maîtrise de SQL et bases solides en Python ou R",
                "Curiosité business et rigueur analytique"
            ),
            perks = listOf("Remboursement transport à 100%", "Accès illimité aux cours en ligne", "Possibilité d'embauche en CDI"),
            isFeatured = false,
            imageUrl = "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&auto=format&fit=crop&q=80",
            companyLogoUrl = "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=200&auto=format&fit=crop&q=80"
        ),
        JobOffer(
            id = "job-8",
            title = "Chef de Projet Événementiel & Partenariats",
            company = "Horizon Événements",
            location = "Strasbourg (67)",
            sector = "Marketing & Vente",
            contractType = "CDD",
            salary = "32 000 € - 36 000 € / an",
            remotePolicy = "Sur site",
            postedAgo = "Il y a 6 jours",
            description = "Pilotez l'organisation logistique et commerciale de congrès professionnels et festivals de grande envergure pour nos clients B2B.",
            missions = listOf(
                "Coordonner les prestataires (technique, traiteurs, hôtellerie, sécurité)",
                "Négocier les accords de sponsoring avec des marques partenaires",
                "Gérer les plannings et les budgets opérationnels sur site"
            ),
            requirements = listOf(
                "Formation Bac+3/5 en événementiel, hôtellerie ou commerce",
                "Première expérience réussie en gestion de projet événementiel",
                "Très grande réactivité et sens aigu du service client"
            ),
            perks = listOf("Déplacements défrayés", "Ambiance d'équipe stimulante"),
            isFeatured = false,
            imageUrl = "https://images.unsplash.com/photo-1511578314322-379afb476865?w=800&auto=format&fit=crop&q=80",
            companyLogoUrl = "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=200&auto=format&fit=crop&q=80"
        )
    )

    val interviewQuestions: List<InterviewQuestion> = listOf(
        InterviewQuestion(
            id = "int-1",
            question = "Parlez-moi de vous et de votre parcours.",
            category = "Classique",
            difficulty = "Débutant",
            recruiterIntent = "Le recruteur évalue votre capacité de synthèse, votre fil conducteur professionnel et la pertinence de votre profil par rapport au poste visé.",
            recommendedAnswer = "Structurez votre réponse en 3 temps (Passé, Présent, Futur) en 90 à 120 secondes maximum : 1) Votre socle de compétences clés, 2) Vos réalisations récentes les plus marquantes, 3) Pourquoi ce poste est l'étape logique suivante.",
            mistakesToAvoid = "Ne récitez pas chronologiquement votre CV depuis le baccalauréat. Évitez les détails personnels superflus et les monologues monotones sans passion.",
            starExample = "Exemple : 'Après 4 ans à diriger des projets agiles chez X où j'ai doublé la cadence de livraison logicielle, je souhaite mettre mon expertise en architecture moderne au service de vos ambitions d'expansion.'"
        ),
        InterviewQuestion(
            id = "int-2",
            question = "Pourquoi souhaitez-vous rejoindre notre entreprise en particulier ?",
            category = "Motivation",
            difficulty = "Débutant",
            recruiterIntent = "Vérifier si vous avez fait des recherches sérieuses sur l'entreprise, ses valeurs, ses produits et si votre motivation est ciblée et authentique.",
            recommendedAnswer = "Citez un projet précis, une actualité récente ou une valeur distinctive de l'entreprise. Montrez le lien direct entre vos aspirations professionnelles et leur feuille de route stratégique.",
            mistakesToAvoid = "Les réponses génériques du type 'vous êtes le leader du secteur' ou 'votre entreprise est réputée'. Ne parlez pas en premier lieu des avantages sociaux ou de la proximité géographique.",
            starExample = "Exemple : 'J'ai suivi avec attention le lancement de votre nouvelle gamme éco-conçue le mois dernier. Vos investissements en R&D durable résonnent parfaitement avec mes travaux menés sur l'optimisation énergétique.'"
        ),
        InterviewQuestion(
            id = "int-3",
            question = "Quels sont vos 2 points forts et votre principal axe d'amélioration ?",
            category = "Piège",
            difficulty = "Intermédiaire",
            recruiterIntent = "Mesurer votre lucidité, votre humilité et votre capacité à progresser face à vos faiblesses professionnelles.",
            recommendedAnswer = "Pour les points forts, illustrez-les immédiatement par un résultat tangible. Pour l'axe d'amélioration, choisissez un vrai défaut professionnel (pas 'trop perfectionniste') et détaillez le plan d'action déjà mis en place pour le corriger.",
            mistakesToAvoid = "Ne dites jamais 'Je n'ai pas de vrai défaut' ni des clichés usés comme 'Je travaille trop'. N'énoncez pas non plus un défaut rédhibitoire pour le poste (ex: 'Je déteste parler aux gens' pour un commercial).",
            starExample = "Exemple de défaut bien géré : 'J'avais tendance à vouloir tout superviser par souci de bien faire. J'ai suivi une formation en délégation et j'utilise désormais un tableau Kanban partagé pour faire confiance à mes collaborateurs.'"
        ),
        InterviewQuestion(
            id = "int-4",
            question = "Racontez-moi une situation où vous avez fait face à un échec ou un conflit.",
            category = "Compétences",
            difficulty = "Avancé",
            recruiterIntent = "Tester votre résilience émotionnelle, votre maturité relationnelle et votre capacité d'apprentissage après une difficulté.",
            recommendedAnswer = "Utilisez impérativement la méthode STAR (Situation, Tâche, Action, Résultat). Assumez votre part de responsabilité sans blâmer les autres, et terminez toujours sur la leçon constructive retenue.",
            mistakesToAvoid = "Rejeter la faute sur son ancien manager ou ses collègues, ou minimiser l'échec en prétendant que ce n'était rien. Gardez un ton calme et factuel.",
            starExample = "S : Retard critique sur une livraison client. T : Rétablir la confiance et livrer un produit stable. A : J'ai convoqué une réunion de crise transparente, priorisé les fonctionnalités MVP avec le client. R : Livraison effectuée 10 jours plus tard avec une note de satisfaction finale de 4.8/5."
        ),
        InterviewQuestion(
            id = "int-5",
            question = "Quelles sont vos prétentions salariales pour ce poste ?",
            category = "Piège",
            difficulty = "Intermédiaire",
            recruiterIntent = "Savoir si vos attentes sont en adéquation avec la grille salariale et évaluer votre sens de la négociation et votre connaissance du marché.",
            recommendedAnswer = "Fournissez une fourchette réaliste (ex: 45K€ - 50K€ fixe) basée sur vos recherches préalables (Apec, Glassdoor, études de rémunération). Précisez que vous tenez compte du package global (primes, intéressement, télétravail).",
            mistakesToAvoid = "Donner un chiffre sec sans justification, se sous-estimer de peur d'être écarté, ou refuser catégoriquement de répondre en renvoyant la balle brutalement.",
            starExample = "Exemple : 'Au regard de mes 4 ans d'expérience et des responsabilités managériales de ce poste, mes prétentions se situent entre 48 000 € et 53 000 € brut annuel, package négociable selon les avantages complémentaires.'"
        ),
        InterviewQuestion(
            id = "int-6",
            question = "Avez-vous des questions pour nous ?",
            category = "Clôture",
            difficulty = "Débutant",
            recruiterIntent = "Évaluer votre curiosité intellectuelle, votre niveau d'engagement et votre projection concrète dans le quotidien de l'équipe.",
            recommendedAnswer = "Répondez TOUJOURS 'Oui !' et posez 2 ou 3 questions stimulantes sur les priorités des 6 premiers mois, la dynamique d'équipe ou les perspectives stratégiques.",
            mistakesToAvoid = "Dire 'Non, tout a été très clair' est une erreur majeure qui traduit un désintérêt. Ne commencez pas immédiatement par les congés payés ou les RTT.",
            starExample = "Exemples percutants : 'Quels seraient les indicateurs clés de succès de ma première année ?' ou 'Quel est le défi prioritaire auquel l'équipe fait face actuellement ?'"
        )
    )

    val cvTemplates: List<CvTemplate> = listOf(
        CvTemplate(
            id = "cv-tech",
            title = "CV Ingénieur & Développeur Tech (ATS-Friendly)",
            sector = "Tech & Digital",
            experienceLevel = "Confirmé (3-5 ans)",
            description = "Structure à fort impact optimisée pour les robots de tri ATS et les recruteurs techniques. Met en avant la stack technique, les métriques concrètes et les projets GitHub.",
            atsScore = 96,
            keyStrengths = listOf(
                "Hiérarchie claire par technologies et résultats chiffrés",
                "Mots-clés techniques indexables facilement par les algorithmes ATS",
                "Section 'Projets & Open Source' valorisante",
                "Format simple en une colonne recommandé par les FAANG"
            ),
            atsTips = listOf(
                "Utilisez des polices standards (Roboto, Inter, Arial) sans fioritures",
                "Évitez les tableaux imbriqués et les graphiques de compétences en barres",
                "Intégrez les mots-clés exacts de l'offre d'emploi ciblée",
                "Enregistrez en format PDF standard généré à partir de texte sélectionnable"
            ),
            summaryTemplate = "Développeur Senior passionné par les architectures mobiles modernes (Kotlin, Compose, Coroutines). 5 ans d'expérience dans la livraison d'applications scalables à fort trafic (>1M d'utilisateurs). Orienté performance, clean code et UX raffinée.",
            experienceTemplate = "DÉVELOPPEUR MOBILE LEAD | TechFlow SAS (2022 - Présent)\n• Refonte complète de l'application de streaming en Jetpack Compose, réduisant le temps de démarrage à froid de 42%.\n• Mise en place de l'architecture MVI modulaire et d'un pipeline CI/CD automatisé sous GitHub Actions.\n• Encadrement technique de 4 développeurs et animation des revues de code.\n\nDÉVELOPPEUR ANDROID | AppStudio (2019 - 2022)\n• Développement de 6 applications B2C natives pour des clients du secteur bancaire et e-commerce.\n• Intégration de paiements sécurisés et de SDK publicitaires AdMob optimisés.",
            skillsTemplate = listOf(
                "Langages: Kotlin, Java, SQL, Python",
                "Frameworks: Jetpack Compose, Android SDK, Coroutines, Flow, Room, Retrofit",
                "Architecture: Clean Architecture, MVVM, MVI, Dependency Injection",
                "Outils: Git, Docker, GitHub Actions, Firebase, Gradle, Figma"
            ),
            educationTemplate = "Master Informatique & Systèmes Mobiles | Polytech (2019)\nCertification Développeur Android Certifié Google (2021)",
            suggestedColors = listOf("#0F172A", "#2563EB", "#10B981")
        ),
        CvTemplate(
            id = "cv-marketing",
            title = "CV Marketing Digital & Communication",
            sector = "Marketing & Vente",
            experienceLevel = "Senior (5+ ans)",
            description = "Mise en avant percutante du retour sur investissement (ROI), des budgets gérés et de la créativité stratégique. Parfait pour les profils Brand, Growth et Social Media.",
            atsScore = 92,
            keyStrengths = listOf(
                "Valorisation des métriques clés (ROAS, CAC, Taux de conversion, Traffic)",
                "Présentation soignée des campagnes primées et des budgets administrés",
                "Accroche profil percutante avec positionnement clair"
            ),
            atsTips = listOf(
                "Précisez les outils martech certifiés (GA4, Meta Business, HubSpot, Semrush)",
                "Quantifiez chaque accomplissement par des pourcentages de croissance",
                "Structurez les intitulés de postes avec les standards du marché"
            ),
            summaryTemplate = "Responsable Marketing Digital avec 7 ans d'expérience dans l'accélération de marques B2B & B2C. Expert en acquisition omnicanale et fidélisation, ayant géré des budgets média de 1.2M€ avec un ROAS moyen de 4.2x.",
            experienceTemplate = "HEAD OF GROWTH & MARKETING | BrandScale (2021 - Présent)\n• Pilotage d'une équipe de 5 spécialistes (SEO, Paid Media, CRM, Content).\n• Augmentation de 135% des leads qualifiés en 18 mois grâce au repositionnement SEO et aux campagnes LinkedIn Ads.\n• Réduction du coût d'acquisition client (CAC) de 28% par l'optimisation des tunnels de conversion.\n\nTRAFFIC MANAGER SENIOR | E-Shop World (2018 - 2021)\n• Gestion opérationnelle de 800K€ de budget annuel sur Google Ads et Meta Ads.\n• Déploiement d'une stratégie de remarketing dynamique générant 450K€ de chiffre d'affaires additionnel.",
            skillsTemplate = listOf(
                "Acquisition: Google Ads, Meta Ads, LinkedIn Ads, SEO technique, TikTok Ads",
                "Data & Analytics: Google Analytics 4, Tag Manager, Looker Studio, Mixpanel",
                "CRM & Automatisation: HubSpot, Klaviyo, Brevo, Salesforce Marketing Cloud",
                "Management: Gestion de budget, Leadership d'équipe, Négociation agences"
            ),
            educationTemplate = "Master 2 Marketing Stratégique & Digital | NEOMA Business School (2017)\nCertifications Google Ads Search & GA4 (2023)",
            suggestedColors = listOf("#1E293B", "#D97706", "#4F46E5")
        ),
        CvTemplate(
            id = "cv-finance",
            title = "CV Finance, Audit & Contrôle de Gestion",
            sector = "Finance & Gestion",
            experienceLevel = "Confirmé (3-6 ans)",
            description = "Mise en page rigoureuse et sobre, conçue pour les cabinets d'audit, directions financières et fonds d'investissement. Met l'accent sur les normes comptables, IFRS et modélisation.",
            atsScore = 95,
            keyStrengths = listOf(
                "Rigueur de présentation exemplaire sans fioritures",
                "Mise en avant des compétences d'analyse financière et de modélisation",
                "Certifications financières internationales visibles immédiatement"
            ),
            atsTips = listOf(
                "Mentionnez les ERP d'entreprise maîtrisés (SAP, Oracle, Sage)",
                "Indiquez le chiffre d'affaires des entités auditées pour donner l'échelle",
                "Restez dans une palette sobre (bleu marine, gris ardoise)"
            ),
            summaryTemplate = "Contrôleur de Gestion bilingue avec 4 ans d'expérience en cabinet d'audit Big 4 puis en filiale internationale. Spécialiste de la clôture financière, des forecasts budgétaires et de l'automatisation Power BI.",
            experienceTemplate = "CONTRÔLEUR DE GESTION | Groupe Aéronautique (2022 - Présent)\n• Supervision des clôtures mensuelles et des reportings financiers pour 3 usines (CA consolidé 120M€).\n• Conception intégrale de 8 tableaux de bord Power BI connectés à SAP S/4HANA, divisant le temps d'analyse par 3.\n• Identification de 450 000 € d'économies d'énergie sur les lignes de production.\n\nAUDITEUR FINANCIER JUNIOR À SENIOR | Deloitte (2019 - 2022)\n• Missions d'audit légal des comptes sociaux et consolidés (normes IFRS et françaises).\n• Analyse des risques de contrôle interne et revue des processus achats/stocks.",
            skillsTemplate = listOf(
                "Outils: SAP S/4HANA, Power BI, Excel Avancé (VBA, Power Query), Hyperion",
                "Expertises: Normes IFRS, Clôture mensuelle, Audit financier, Modélisation LBO",
                "Langues: Français (Maternel), Anglais (C1 Courant - 940 TOEIC)"
            ),
            educationTemplate = "Diplôme Supérieur de Comptabilité et de Gestion (DSCG) (2020)\nMaster Finance d'Entreprise | Université Paris-Dauphine (2019)",
            suggestedColors = listOf("#0F172A", "#0369A1", "#334155")
        ),
        CvTemplate(
            id = "cv-reconversion",
            title = "CV Reconversion & Jeune Diplômé",
            sector = "Tous secteurs",
            experienceLevel = "Junior / Reconversion",
            description = "Structure par compétences transférables (Soft skills et Hard skills) pour combler le manque d'expérience directe et valoriser les réussites passées et projets récents.",
            atsScore = 94,
            keyStrengths = listOf(
                "Section 'Compétences transversales' mise en tête de page",
                "Valorisation des projets personnels, certifications et stages",
                "Accroche positive soulignant la motivation et la capacité d'apprentissage"
            ),
            atsTips = listOf(
                "Traduisez vos expériences passées avec le vocabulaire du nouveau métier",
                "Mettez en avant vos certifications récentes et votre portfolio",
                "Ne cachez pas votre reconversion, faites-en un gage de maturité et d'agilité"
            ),
            summaryTemplate = "Professionnel dynamique en reconversion après 6 ans en gestion de la relation client. Récemment certifié et formé aux méthodes agiles et aux outils numériques modernes. Rigoureux, curieux et immédiatement opérationnel.",
            experienceTemplate = "PROJET PROFESSIONNEL CERTIFIANT | Bootcamp Numérique (2023 - 2024)\n• Conception et gestion de A à Z d'une plateforme collaborative en équipe de 4 personnes.\n• Rédaction des cahiers des charges, wireframes Figma et suivi de projet sous Jira.\n\nRESPONSABLE RELATION CLIENT | Retail Group (2018 - 2023)\n• Gestion d'un portefeuille de 200 clients B2B avec un taux de rétention de 94%.\n• Résolution des litiges complexes et formation de 8 nouveaux collaborateurs.",
            skillsTemplate = listOf(
                "Hard Skills: Outils collaboratifs (Slack, Notion, Jira), Méthodes Agiles (Scrum, Kanban)",
                "Soft Skills: Aisance relationnelle, Résolution de problèmes, Adaptabilité rapide, Négociation",
                "Outils: Pack Office 365, Canva, Figma (Bases), Trello"
            ),
            educationTemplate = "Titre Professionnel RNCP Niveau 6 (Bac+3/4) | 2024\nLicence Administration Économique et Sociale | Université (2018)",
            suggestedColors = listOf("#1E293B", "#059669", "#D97706")
        )
    )

    val interviewChecklist = listOf(
        "J-7: Étudier en profondeur le site web, l'actualité et les concurrents de l'entreprise",
        "J-5: Préparer 3 exemples concrets de réalisations chiffrées selon la méthode STAR",
        "J-3: Ajuster sa tenue vestimentaire en fonction de la culture de l'entreprise",
        "J-1: Repérer le trajet ou tester sa connexion webcam et son micro si entretien vidéo",
        "Jour J (H-30min): Arriver 10 à 15 minutes en avance, ni trop tôt ni en retard",
        "Pendant l'entretien: Maintenir le contact visuel, sourire et prendre des notes avec parcimonie",
        "Fin d'entretien: Poser au moins 2 questions pertinentes sur les enjeux du poste",
        "H+24: Envoyer un email de remerciement concis réaffirmant votre motivation"
    )
}
