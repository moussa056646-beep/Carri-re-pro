package com.example.data.repository

import com.example.data.local.entity.MarketingExerciseEntity
import com.example.data.local.entity.MarketingLessonEntity
import com.example.data.local.entity.MarketingResourceEntity

object MarketingDataProvider {

    fun getDefaultLessons(): List<MarketingLessonEntity> {
        return listOf(
            MarketingLessonEntity(
                id = "lesson_seo_01",
                moduleCategory = "SEO",
                titleFr = "SEO Moderne : Dominer Google & l'Intention de Recherche",
                titleEn = "Modern SEO: Dominating Google & Search Intent",
                subtitleFr = "Comprendre les algorithmes, l'EEAT et positionner ses pages en première position.",
                subtitleEn = "Understand ranking algorithms, EEAT and rank #1 organically.",
                durationMinutes = 20,
                level = "Intermédiaire",
                isCompleted = false,
                isFavorite = false,
                videoTitle = "Masterclass SEO : De 0 à 50 000 visiteurs mensuels",
                videoDuration = "14:25",
                videoThumbnailUrl = "https://images.unsplash.com/photo-1571786256017-aee7a0c009b6?w=800&auto=format&fit=crop&q=80",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                videoChapters = "00:00|Introduction & Algorithme;;02:45|Recherche sémantique & Intentions;;06:10|Optimisation On-Page & Balises;;09:40|Netlinking & Autorité de Domaine;;12:15|Checklist d'audit rapide",
                contentMarkdownFr = """
                    ### 🎯 Les Fondations du Référencement Naturel en 2026

                    Le SEO moderne ne consiste plus à répéter des mots-clés bêtement, mais à satisfaire **l'intention de recherche** de l'utilisateur avec la meilleure expérience possible.

                    #### 1. Les 4 Types d'Intentions de Recherche
                    * **Informationnelle** : L'utilisateur cherche à apprendre (*ex: "comment calculer le ROAS"*).
                    * **Navigationnelle** : Recherche d'un site précis (*ex: "connexion LinkedIn"*).
                    * **Commerciale** : En phase de comparaison (*ex: "meilleurs outils emailing 2026"*).
                    * **Transactionnelle** : Prêt à acheter (*ex: "formation marketing digital certifiante prix"*).

                    #### 2. Le Cadre Google EEAT
                    * **Expérience** : Preuves vécues, cas concrets, témoignages authentiques.
                    * **Expertise** : Compétences démontrées de l'auteur.
                    * **Autorité** : Backlinks thématiques de sites réputés.
                    * **Confiance (Trust)** : Transparence des mentions légales, sécurisation HTTPS et avis certifiés.

                    #### 3. Optimisation Sémantique & Structurelle
                    Structurez toujours vos articles avec une balise `H1` unique, des balises `H2` et `H3` logiques, et des métadonnées soignées (`Title` entre 50 et 60 caractères, `Meta Description` attractive sous 155 caractères).
                """.trimIndent(),
                contentMarkdownEn = """
                    ### 🎯 Foundations of Modern SEO in 2026

                    Modern SEO is no longer about keyword stuffing, but satisfying **Search Intent** with the absolute best user experience.

                    #### 1. The 4 Search Intent Types
                    * **Informational**: User wants to learn (*e.g., "how to calculate ROAS"*).
                    * **Navigational**: User searches a brand (*e.g., "LinkedIn login"*).
                    * **Commercial**: User compares choices (*e.g., "best emailing software 2026"*).
                    * **Transactional**: User wants to buy (*e.g., "buy digital marketing masterclass"*).

                    #### 2. Google's EEAT Framework
                    Experience, Expertise, Authoritativeness, and Trustworthiness determine ranking stability.
                """.trimIndent(),
                keyTakeawaysFr = "• Toujours aligner le contenu avec l'intention exacte de la requête\n• Viser un score EEAT irréprochable avec des cas d'usage réels\n• Optimiser les balises Title (50-60 car.) et H1/H2\n• Construire un cocon sémantique et des liens entrants de qualité",
                keyTakeawaysEn = "• Match user intent precisely\n• Build EEAT signals and practical case studies\n• Optimize Title tags and clean hierarchy\n• Cultivate high authority contextual backlinks",
                orderIndex = 1
            ),
            MarketingLessonEntity(
                id = "lesson_sea_02",
                moduleCategory = "SEA_ADS",
                titleFr = "Google Ads & Campagnes Search Rentables",
                titleEn = "Google Ads & Profitable Search Campaigns",
                subtitleFr = "Structure de compte, ciblage par mots-clés et pilotage au ROAS & CPA cible.",
                subtitleEn = "Account structure, keyword matching and steering by target ROAS & CPA.",
                durationMinutes = 25,
                level = "Intermédiaire",
                isCompleted = false,
                isFavorite = false,
                videoTitle = "Google Ads de A à Z : Créer une campagne qui convertit",
                videoDuration = "18:40",
                videoThumbnailUrl = "https://images.unsplash.com/photo-1551836022-d5d88e9218df?w=800&auto=format&fit=crop&q=80",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                videoChapters = "00:00|Comprendre le modèle d'enchère;;03:15|Structure Campagne / Groupes d'annonces;;07:30|Types de correspondance de mots-clés;;11:20|Créer des annonces responsives (RSA);;15:10|Optimiser le Quality Score",
                contentMarkdownFr = """
                    ### 💰 Dominez Google Ads (SEA) et Maximisez votre ROAS

                    Le SEA (*Search Engine Advertising*) offre une visibilité instantanée. Pour ne pas gaspiller son budget, il faut maîtriser la formule du **Quality Score** et la structure des groupes d'annonces.

                    #### 1. Le Quality Score (Niveau de Qualité sur 10)
                    Il est composé de 3 piliers essentiels :
                    1. **Taux de clic attendu (CTR)** : Vos annonces suscitent-elles l'intérêt ?
                    2. **Pertinence de l'annonce** : Le texte correspond-il aux mots-clés tapés ?
                    3. **Convivialité de la page de destination (Landing Page)** : Vitesse, clarté de l'offre et facilité d'achat.

                    #### 2. Types de Mots-Clés
                    * `[mot-clé]` : Mot-clé exact (trafic très qualifié, volume modéré).
                    * `"mot-clé"` : Expression exacte (bon compromis précision/volume).
                    * `mot-clé` : Requête large (à utiliser avec précaution et une liste de mots-clés négatifs stricte).

                    #### 3. Annonces Responsives sur le Réseau de Recherche (RSA)
                    Fournissez 15 titres variés et 4 descriptions. L'algorithme combine automatiquement les variantes les plus performantes.
                """.trimIndent(),
                contentMarkdownEn = """
                    ### 💰 Google Ads Strategy: Maximize Your ROAS

                    Learn how Quality Score affects your Cost Per Click (CPC) and how to design winning Responsive Search Ads.
                """.trimIndent(),
                keyTakeawaysFr = "• Le Quality Score réduit drastiquement votre CPC réel\n• Segmenter les groupes d'annonces par intention thématique précise\n• Toujours exclure les mots-clés négatifs pour éliminer les clics inutiles\n• Piloter au ROAS (Chiffre d'affaires généré / Dépenses pub)",
                keyTakeawaysEn = "• High Quality Score reduces real CPC\n• Tightly theme ad groups by search intent\n• Maintain aggressive negative keyword lists\n• Track ROAS and value-based bidding",
                orderIndex = 2
            ),
            MarketingLessonEntity(
                id = "lesson_social_03",
                moduleCategory = "SOCIAL_MEDIA",
                titleFr = "Social Media : Création de Contenu & Rétention",
                titleEn = "Social Media: Content Strategy & Retention",
                subtitleFr = "Méthodes de viralité pour TikTok, Instagram Reels et LinkedIn B2B.",
                subtitleEn = "Viral frameworks for TikTok, Instagram Reels and LinkedIn B2B.",
                durationMinutes = 18,
                level = "Débutant",
                isCompleted = false,
                isFavorite = false,
                videoTitle = "La Stratégie Vidéo Courte : Les 3 premières secondes cruciales",
                videoDuration = "12:10",
                videoThumbnailUrl = "https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=800&auto=format&fit=crop&q=80",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                videoChapters = "00:00|Les algorithmes de flux de découverte;;02:30|L'art du Hook visuel et textuel;;05:45|Rétention : la courbe de visionnage;;08:30|Call to Action : comment convertir son audience;;10:50|Planification hebdomadaire",
                contentMarkdownFr = """
                    ### 📱 Construire une Audience et Convertir sur les Réseaux Sociaux

                    Que ce soit sur TikTok, Instagram ou LinkedIn, l'algorithme valorise désormais deux métriques reines : **le temps de rétention** et **le ratio partages/enregistrements**.

                    #### 1. L'anatomie d'un contenu performant
                    * **Le Hook (0 à 3 secondes)** : Suscite la curiosité, brise un mythe ou promet un bénéfice concret.
                    * **Le Corps (Valeur dense)** : Rythme dynamique, changements de plans, storytelling ou étapes faciles à mémoriser.
                    * **La Rétention (Watch Time)** : Maintenir l'attention jusqu'au bout grâce à une boucle narrative.
                    * **Le Call To Action (CTA)** : Inciter à sauvegarder ("Enregistre pour plus tard") ou à commenter pour recevoir une ressource.

                    #### 2. La Matrice TOFU - MOFU - BOFU
                    * **TOFU (Top of Funnel)** : Contenu grand public pour attirer de nouveaux regards.
                    * **MOFU (Middle of Funnel)** : Contenu expert, coulisses et preuves d'autorité.
                    * **BOFU (Bottom of Funnel)** : Études de cas, démonstrations d'offres et témoignages clients.
                """.trimIndent(),
                contentMarkdownEn = """
                    ### 📱 Building and Monetizing Social Media Audiences

                    Master the science of short-form video hooks, retention curves, and conversion funnels on modern platforms.
                """.trimIndent(),
                keyTakeawaysFr = "• Les 3 premières secondes déterminent 80% du succès d'une vidéo\n• Privilégier les enregistrements et partages plutôt que les simples likes\n• Varier entre contenus de découverte (TOFU) et de conversion (BOFU)\n• Maintenir une régularité éditoriale avec des formats récurrents",
                keyTakeawaysEn = "• First 3 seconds dictate 80% of video reach\n• Prioritize saves and shares over vanity likes\n• Balance brand awareness with high-intent case studies\n• Consistency beats sporadic perfection",
                orderIndex = 3
            ),
            MarketingLessonEntity(
                id = "lesson_emailing_04",
                moduleCategory = "EMAILING",
                titleFr = "Email Marketing & Séquences Automatisées",
                titleEn = "Email Marketing & Automated Funnels",
                subtitleFr = "Concevoir une séquence de bienvenue irrésistible et fidéliser vos abonnés.",
                subtitleEn = "Design high-converting welcome sequences and nurture customers.",
                durationMinutes = 22,
                level = "Intermédiaire",
                isCompleted = false,
                isFavorite = false,
                videoTitle = "Emailing : Doubler son taux d'ouverture et générer des ventes en pilote automatique",
                videoDuration = "15:50",
                videoThumbnailUrl = "https://images.unsplash.com/photo-1596524430615-b46475ddff6e?w=800&auto=format&fit=crop&q=80",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                videoChapters = "00:00|Délivrabilité : passer les filtres anti-spam;;03:10|L'objet et le preheader parfaits;;06:40|La séquence de bienvenue en 5 emails;;10:15|Segmentation comportementale;;13:20|Nettoyage et hygiène de base de données",
                contentMarkdownFr = """
                    ### ✉️ L'Email Marketing : Le Canal avec le Plus Fort ROI (40:1)

                    Contrairement aux réseaux sociaux où vous dépendez des algorithmes, votre liste d'abonnés est un actif propriétaire pérenne.

                    #### 1. Les 3 Règles d'Or de l'Objet d'Email
                    * **Longueur optimale** : Moins de 45 caractères pour un affichage parfait sur mobile.
                    * **Curiosité ou Bénéfice direct** : Éviter les titres corporate fades.
                    * **Preheader soigné** : Compléter l'objet pour former une phrase d'accroche cohérente.

                    #### 2. La Séquence de Bienvenue (Soap Opera Sequence)
                    1. **Email 1** : Livraison immédiate du Lead Magnet + Accueil chaleureux.
                    2. **Email 2** : Histoire personnelle et grand défi surmonté.
                    3. **Email 3** : Révélation de l'épiphanie / méthode clé.
                    4. **Email 4** : Bénéfices cachés et étude de cas client.
                    5. **Email 5** : Offre exclusive avec urgence éthique.
                """.trimIndent(),
                contentMarkdownEn = """
                    ### ✉️ Email Marketing: 40x ROI Channel

                    Unlock high open rates, automated customer onboarding, and clean deliverability habits.
                """.trimIndent(),
                keyTakeawaysFr = "• L'email reste le levier avec le retour sur investissement le plus élevé\n• Toujours soigner le pré-en-tête (preheader) visible sur smartphone\n• Segmenter en fonction de l'engagement réel (ouvreurs des 90 derniers jours)\n• Respecter scrupuleusement le consentement RGPD et le lien de désinscription",
                keyTakeawaysEn = "• Email delivers the highest historical marketing ROI\n• Craft punchy preheaders for mobile preview panes\n• Segment lists by recent behavioral engagement\n• Respect privacy laws and maintain clear unsubscribe links",
                orderIndex = 4
            ),
            MarketingLessonEntity(
                id = "lesson_analytics_05",
                moduleCategory = "ANALYTICS",
                titleFr = "Google Analytics 4 & Pilotage des Conversions",
                titleEn = "Google Analytics 4 & Conversion Tracking",
                subtitleFr = "Maîtriser les événements personnalisés, l'attribution et l'analyse de tunnel.",
                subtitleEn = "Master custom events, marketing attribution and funnel analysis.",
                durationMinutes = 24,
                level = "Expert",
                isCompleted = false,
                isFavorite = false,
                videoTitle = "Maîtriser GA4 : Trouver les failles de son tunnel de vente",
                videoDuration = "17:15",
                videoThumbnailUrl = "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&auto=format&fit=crop&q=80",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                videoChapters = "00:00|Du modèle session au modèle événementiel;;03:30|Configurer les conversions majeures;;07:15|Rapports d'entonnoir d'exploration;;11:00|Modèles d'attribution (Dernier clic vs Data-driven);;14:30|Calculer le CAC et la LTV",
                contentMarkdownFr = """
                    ### 📊 Piloter sa Croissance par la Donnée avec GA4

                    Google Analytics 4 repose intégralement sur un modèle événementiel (*event-driven*). Tout clic, défilement ou achat est un événement horodaté avec des paramètres spécifiques.

                    #### 1. Métriques Indispensables
                    * **Taux d'engagement** : Pourcentage de sessions durant plus de 10 secondes ou ayant au moins 2 pages vues ou 1 conversion.
                    * **CAC (Coût d'Acquisition Client)** : `Dépenses Marketing Totales / Nouveaux Clients Acquis`.
                    * **LTV (Lifetime Value)** : Valeur moyenne générée par un client sur toute la durée de sa relation.
                    * **ROAS (Return On Ad Spend)** : `Chiffre d'Affaires Généré / Coût Publicitaire`.

                    #### 2. L'Attribution Data-Driven
                    L'attribution basée sur les données utilise l'apprentissage automatique pour accorder du crédit à chaque point de contact du parcours d'achat, plutôt que de tout attribuer injustement au dernier clic.
                """.trimIndent(),
                contentMarkdownEn = """
                    ### 📊 Data-Driven Growth with Google Analytics 4

                    Understand event-based schemas, exploratory funnel drop-offs, CAC, LTV and multi-touch attribution.
                """.trimIndent(),
                keyTakeawaysFr = "• Se concentrer sur le taux d'engagement plutôt que sur le taux de rebond classique\n• Valider la remontée des événements e-commerce dans DebugView\n• Comparer systématiquement le CAC avec la Lifetime Value (LTV > 3x CAC)\n• Exploiter les entonnoirs d'exploration pour repérer les fuites de panier",
                keyTakeawaysEn = "• Focus on engagement rate and value per session\n• Test tracking tags via Google Tag Manager and DebugView\n• Ensure LTV is at least 3x Customer Acquisition Cost (CAC)\n• Use funnel explorations to isolate checkout drop-offs",
                orderIndex = 5
            ),
            MarketingLessonEntity(
                id = "lesson_copywriting_06",
                moduleCategory = "COPYWRITING",
                titleFr = "Copywriting & Psychologie de la Vente",
                titleEn = "Copywriting & Sales Psychology",
                subtitleFr = "Écrire des textes persuasifs avec les formules AIDA, PAS et la preuve sociale.",
                subtitleEn = "Write persuasive sales copy using AIDA, PAS and social proof.",
                durationMinutes = 20,
                level = "Débutant",
                isCompleted = false,
                isFavorite = false,
                videoTitle = "Copywriting : Les mots qui déclenchent l'action d'achat",
                videoDuration = "13:55",
                videoThumbnailUrl = "https://images.unsplash.com/photo-1455390582262-044cdead277a?w=800&auto=format&fit=crop&q=80",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyBlazes.mp4",
                videoChapters = "00:00|Clarté contre créativité;;02:40|La formule AIDA (Attention, Intérêt, Désir, Action);;05:30|La formule PAS (Problème, Agitation, Solution);;08:45|Désarmer les objections d'achat;;11:20|Créer des boutons d'action (CTA) magnétiques",
                contentMarkdownFr = """
                    ### ✍️ L'Art du Copywriting : Vendre avec les Mots

                    Le copywriting n'est pas de la littérature : c'est l'art d'amener un lecteur à poser une action précise (s'inscrire, acheter, réserver un appel).

                    #### 1. La Formule AIDA
                    * **Attention** : Une accroche qui stoppe le regard.
                    * **Intérêt** : Un fait intriguant ou une histoire captivante.
                    * **Désir** : Visualiser les bénéfices concrets et la transformation obtenue.
                    * **Action** : Un appel clair, sans friction et rassurant.

                    #### 2. La Formule PAS (Problème - Agitation - Solution)
                    Idéale pour les pages de vente et les emails de conversion :
                    1. Identifiez la frustration précise de votre prospect.
                    2. Décrivez les conséquences désagréables de l'inaction.
                    3. Présentez votre offre comme la solution naturelle et éprouvée.
                """.trimIndent(),
                contentMarkdownEn = """
                    ### ✍️ Copywriting Mastery: Words that Convert

                    Learn the psychological blueprints that drive customer decisions: AIDA, PAS, and high-impact CTAs.
                """.trimIndent(),
                keyTakeawaysFr = "• La clarté bat toujours la créativité ou l'humour vague\n• Parler des bénéfices pour le client plutôt que des caractéristiques techniques\n• Rassurer avec des garanties et des témoignages authentiques\n• Un seul objectif et un seul appel à l'action principal par page",
                keyTakeawaysEn = "• Clarity always outperforms cleverness\n• Sell the transformation, not the features\n• Eliminate risk using clear guarantees and proof\n• Maintain one single primary call to action per message",
                orderIndex = 6
            )
        )
    }

    fun getDefaultExercises(): List<MarketingExerciseEntity> {
        return listOf(
            MarketingExerciseEntity(
                id = "ex_seo_audit",
                lessonId = "lesson_seo_01",
                moduleCategory = "SEO",
                titleFr = "Audit SEO On-Page : Détectez les anomalies",
                titleEn = "On-Page SEO Audit: Spot Critical Issues",
                descriptionFr = "Analysez une page e-commerce fictive et identifiez les 4 erreurs SEO majeures pénalisant son classement.",
                descriptionEn = "Analyze a mock landing page and detect the 4 primary SEO bottlenecks.",
                exerciseType = "AUDIT_SEO",
                instructionsFr = "Parcourez le code et le contenu de la fiche produit. Cochez les éléments qui violent les bonnes pratiques de Google.",
                instructionsEn = "Review the page snippet and select all violations.",
                solutionExplanationFr = "Les 4 erreurs critiques étaient : 1. Balise Title de 85 caractères (tronquée sur Google). 2. Absence totale de balise H1. 3. Image produit sans attribut 'alt' pour les robots. 4. Aucune ancre de maillage interne vers la catégorie parente.",
                solutionExplanationEn = "The critical errors were: 1. Title tag too long (85 chars). 2. Missing H1 tag. 3. Missing image alt attribute. 4. Zero internal link to parent category.",
                isCompleted = false,
                bestScore = 0,
                xpReward = 75
            ),
            MarketingExerciseEntity(
                id = "ex_ads_writer",
                lessonId = "lesson_sea_02",
                moduleCategory = "SEA_ADS",
                titleFr = "Atelier Rédacteur d'Annonce Google Ads",
                titleEn = "Google Ads Copywriting Workshop",
                descriptionFr = "Rédigez 2 titres percutants (max 30 car.) et 1 description (max 90 car.) pour un service de coaching professionnel.",
                descriptionEn = "Write 2 punchy headlines (max 30 chars) and 1 description (max 90 chars) respecting character limits.",
                exerciseType = "ADS_COPYWRITER",
                instructionsFr = "Respectez scrupuleusement les contraintes de caractères de Google Ads tout en intégrant un mot-clé fort et un appel à l'action clair.",
                instructionsEn = "Stay strictly within character limits while providing an engaging call to action.",
                solutionExplanationFr = "Exemple optimisé : Titre 1: 'Coaching Emploi Certifié' (23/30) • Titre 2: 'Boostez Votre Salaire' (21/30) • Description: 'Préparez vos entretiens et décrochez le job idéal avec un expert RH. Bilan offert !' (83/90).",
                solutionExplanationEn = "Optimized sample: Headline 1: 'Certified Career Coaching' • Headline 2: 'Boost Your Salary Now' • Description: 'Ace your next job interview with seasoned career coaches. Free initial consultation!'.",
                isCompleted = false,
                bestScore = 0,
                xpReward = 80
            ),
            MarketingExerciseEntity(
                id = "ex_roas_calc",
                lessonId = "lesson_analytics_05",
                moduleCategory = "ANALYTICS",
                titleFr = "Simulateur Décisionnel de Rentabilité (ROAS & ROI)",
                titleEn = "Profitability & ROAS Decision Simulator",
                descriptionFr = "Entrez vos budgets marketing, votre CA généré et votre marge pour tester la viabilité économique d'une campagne.",
                descriptionEn = "Calculate real ROAS, CPA, and net profit to make strategic scale decisions.",
                exerciseType = "ROAS_CALCULATOR",
                instructionsFr = "Ajustez les curseurs de dépenses et analysez en temps réel le ROAS, le seuil de rentabilité et la recommandation d'arbitrage.",
                instructionsEn = "Tweak budget and margin parameters to simulate campaign scale decisions.",
                solutionExplanationFr = "Un ROAS supérieur à 400% (4:1) avec une marge brute de 60% est généralement un signal vert pour augmenter le budget de 20% par semaine (scaling progressif).",
                solutionExplanationEn = "A ROAS > 400% with 60% gross margin confirms a healthy campaign ready for scaling.",
                isCompleted = false,
                bestScore = 0,
                xpReward = 85
            ),
            MarketingExerciseEntity(
                id = "ex_certif_quiz",
                lessonId = "lesson_copywriting_06",
                moduleCategory = "CERTIFICATION",
                titleFr = "Grand Quiz de Certification Marketing Digital (10 Questions)",
                titleEn = "Digital Marketing Certification Quiz (10 Questions)",
                descriptionFr = "Testez l'ensemble de vos connaissances en SEO, Ads, Réseaux Sociaux, Emailing et Analytics. Validez au moins 80% pour décrocher le badge !",
                descriptionEn = "Comprehensive test covering SEO, Ads, Social, Email and Analytics. Score 80%+ to unlock the expert badge!",
                exerciseType = "CERTIFICATION_QUIZ",
                instructionsFr = "Répondez aux 10 questions à choix multiples. Chaque réponse est instantanément validée avec justification pédagogique.",
                instructionsEn = "Answer the 10 multiple-choice questions with real-time feedback.",
                solutionExplanationFr = "Bravo ! Ce quiz évalue les compétences demandées aux Traffic Managers, Growth Hackers et Responsables Marketing Digital.",
                solutionExplanationEn = "Congratulations! This quiz covers industry standard skills for modern digital marketers.",
                isCompleted = false,
                bestScore = 0,
                xpReward = 150
            )
        )
    }

    fun getDefaultResources(): List<MarketingResourceEntity> {
        return listOf(
            MarketingResourceEntity(
                id = "res_seo_checklist",
                titleFr = "Checklist d'Audit SEO 2026 (35 Points Clés)",
                titleEn = "2026 SEO Audit Checklist (35 Key Checks)",
                category = "CHECKLIST",
                descriptionFr = "La liste complète des vérifications techniques, sémantiques et netlinking avant de lancer ou refondre un site.",
                descriptionEn = "Full technical, content and link profile checklist prior to site launch.",
                badgeLabel = "SEO • Pratique",
                fileSize = "1.8 Mo (In-App)",
                downloadUrl = "https://example.com/resources/checklist_seo_2026.pdf",
                contentBodyFr = """
                    ### 📋 Checklist d'Audit SEO en 35 Points

                    #### 1. Audit Technique & Indexation
                    [ ] 1. Fichier `robots.txt` accessible et non bloquant pour les pages clés.
                    [ ] 2. Plan de site XML (`sitemap.xml`) soumis dans Google Search Console.
                    [ ] 3. Certificat HTTPS actif sans avertissement de contenu mixte.
                    [ ] 4. Temps de chargement inférieur à 2,5 secondes (Score Core Web Vitals LCP).
                    [ ] 5. Site 100% Mobile-Friendly (testé sur viewport 360px).
                    [ ] 6. Présence d'une balise canonique `<link rel="canonical">` sur chaque page.
                    [ ] 7. Absence de pages d'erreur 404 dans les liens internes.
                    [ ] 8. Redirections 301 directes (éviter les chaînes de redirection).

                    #### 2. Optimisation Sémantique On-Page
                    [ ] 9. Balise `<title>` unique entre 50 et 60 caractères avec le mot-clé principal au début.
                    [ ] 10. `meta description` persuasive entre 130 et 155 caractères incitant au clic.
                    [ ] 11. Une seule et unique balise `<h1>` par page reprenant l'intention principale.
                    [ ] 12. Structure logique des titres `<h2>`, `<h3>` sans sauter de niveau.
                    [ ] 13. Attribut `alt` descriptif renseigné sur toutes les images importantes.
                    [ ] 14. URL courtes, lisibles, sans caractères accentués ni paramètres superflus.
                    [ ] 15. Données structurées Schema.org (Article, FAQPage, Product ou Breadcrumb).

                    #### 3. Maillage Interne & Autorité
                    [ ] 16. Au moins 3 à 5 liens internes pertinents par article vers des pages mères.
                    [ ] 17. Ancres de liens internes textuelles et contextualisées.
                    [ ] 18. Absence de pages orphelines (pages sans aucun lien interne entrant).
                    [ ] 19. Backlinks acquis auprès de sites d'autorité thématique similaire.
                    [ ] 20. Profil de liens naturel sans sur-optimisation d'ancres exactes.
                """.trimIndent(),
                contentBodyEn = """
                    ### 📋 35-Point Comprehensive SEO Checklist

                    Technical infrastructure, crawlability, content hierarchy and backlink safety measures.
                """.trimIndent()
            ),
            MarketingResourceEntity(
                id = "res_social_calendar",
                titleFr = "Modèle de Calendrier Éditorial Réseaux Sociaux",
                titleEn = "Social Media Editorial Calendar Template",
                category = "TEMPLATE",
                descriptionFr = "Matrice hebdomadaire pour organiser vos formats Reels, Carrousels, Stories et Posts B2B.",
                descriptionEn = "Weekly framework for organizing short videos, carousels, and B2B discussions.",
                badgeLabel = "Social Media",
                fileSize = "2.2 Mo (In-App)",
                downloadUrl = "https://example.com/resources/social_media_calendar_template.pdf",
                contentBodyFr = """
                    ### 🗓️ Modèle de Calendrier Éditorial Réseaux Sociaux

                    #### Structure de Répartition Recommandée (Règle 40 - 40 - 20)
                    * **40% Valeur & Éducation** : Tutoriels, conseils pratiques, infographies, résumés d'expérience.
                    * **40% Connexion & Divertissement** : Coulisses d'entreprise, anecdotes, avis tranchés, sondages.
                    * **20% Vente & Promotion** : Témoignages clients, démonstration de produit, invitation à l'action.

                    #### Matrice de la Semaine Type
                    * **Lundi (Inspiration & Énergie)** : Post d'opinion ou retour d'expérience fort pour démarrer la semaine.
                    * **Mardi (Tutoriel Actionnable)** : Carrousel 5 slides ou Reel pas-à-pas avec ressource à télécharger.
                    * **Mercredi (Étude de Cas)** : Raconter l'histoire d'un client avant / après avoir utilisé votre solution.
                    * **Jeudi (Débat & Engagement)** : Question ouverte clivante ou sondage sur une pratique du secteur.
                    * **Vendredi (Coulisses & Culture)** : Photo ou vidéo de l'équipe, anecdotes décontractées.
                    * **Week-end (Récapitulatif)** : Newsletter hebdomadaire ou rediffusion du meilleur contenu de la semaine.
                """.trimIndent(),
                contentBodyEn = """
                    ### 🗓️ Social Media Content Calendar Framework

                    Balance educational value, authority building and promotional offers efficiently.
                """.trimIndent()
            ),
            MarketingResourceEntity(
                id = "res_email_swipefile",
                titleFr = "Swipe File de 25 Objets d'Emails à Fort Taux d'Ouverture",
                titleEn = "Swipe File of 25 High-Converting Email Subject Lines",
                category = "CHEAT_SHEET",
                descriptionFr = "Formules testées générant plus de 45% d'ouverture : curiosité, urgence, contre-intuitif et bénéfice.",
                descriptionEn = "Proven formulas generating >45% open rates across industries.",
                badgeLabel = "Copywriting & Email",
                fileSize = "1.1 Mo (In-App)",
                downloadUrl = "https://example.com/resources/email_swipe_file.pdf",
                contentBodyFr = """
                    ### 📬 25 Objets d'Emails qui Cartonnent (>45% d'Ouverture)

                    #### Catégorie 1 : La Curiosité Pure
                    1. "Une erreur que 90% des marketers commettent encore..."
                    2. "Ne lisez pas cet email si vous avez déjà trop de clients."
                    3. "Ce que Google ne vous dit pas sur son nouvel algorithme."
                    4. "Mauvaise nouvelle (et comment en tirer profit)"
                    5. "Pourquoi j'ai supprimé la moitié de mes campagnes hier"

                    #### Catégorie 2 : Le Bénéfice Direct & Mesurable
                    6. "Comment passer de 2% à 7% de conversion en 48h"
                    7. "Le template exact de notre page de vente à 100k€"
                    8. "3 hacks simples pour diviser votre CPA par deux"
                    9. "Voici ma checklist d'audit SEO prête à l'emploi (PDF)"
                    10. "La méthode en 15 minutes pour rédiger une newsletter"

                    #### Catégorie 3 : L'Effet Contre-Intuitif
                    11. "Arrêtez de publier tous les jours sur les réseaux..."
                    12. "Pourquoi avoir moins de trafic m'a fait gagner plus d'argent"
                    13. "Le piège des influenceurs à 100k abonnés"
                    14. "Pourquoi vos meilleurs clients n'achètent jamais au premier clic"

                    #### Catégorie 4 : L'Urgence & Événement
                    15. "[Dernière chance] La masterclass ferme ce soir à 23h59"
                    16. "Vous avez oublié quelque chose dans votre panier ?"
                    17. "Votre accès expire dans 6 heures..."
                """.trimIndent(),
                contentBodyEn = """
                    ### 📬 25 Proven Email Subject Lines (>45% Open Rate)

                    Curiosity-driven, benefit-oriented and contrarian hooks ready to copy and paste.
                """.trimIndent()
            ),
            MarketingResourceEntity(
                id = "res_marketing_glossary",
                titleFr = "Glossaire Essentiel du Marketing Digital & Growth",
                titleEn = "Digital Marketing & Growth Glossary",
                category = "GUIDE_PDF",
                descriptionFr = "Toutes les définitions clés : CAC, LTV, ROAS, CPA, CTR, CVR, Pixel, Retargeting, A/B Testing, Churn...",
                descriptionEn = "Essential terminology: CAC, LTV, ROAS, CPA, CTR, CVR, Retargeting, Churn...",
                badgeLabel = "Référence",
                fileSize = "1.5 Mo (In-App)",
                downloadUrl = "https://example.com/resources/glossaire_marketing_digital.pdf",
                contentBodyFr = """
                    ### 📖 Le Glossaire Définitif du Marketing Digital

                    * **ROAS (Return On Ad Spend)** : Chiffre d'affaires généré divisé par le coût publicitaire. *Ex: 5000€ de ventes pour 1000€ dépensés = ROAS de 500% (ou 5:1).*
                    * **CAC (Coût d'Acquisition Client)** : Coût total de marketing et ventes divisé par le nombre de nouveaux clients acquis.
                    * **LTV (Lifetime Value)** : Montant total net qu'un client rapporte durant toute sa vie de consommateur chez vous.
                    * **CTR (Click-Through Rate)** : Taux de clic = `(Clics / Impressions) x 100`. Sur Google Ads, un bon CTR Search dépasse 4-5%.
                    * **CVR (Conversion Rate)** : Taux de conversion = `(Nombre de conversions / Visiteurs uniques) x 100`.
                    * **CPC (Coût Par Clic)** : Montant payé à la plateforme publicitaire chaque fois qu'un internaute clique sur l'annonce.
                    * **CPA (Coût Par Action / Acquisition)** : Dépense nécessaire pour générer une vente ou un lead qualifié.
                    * **A/B Testing** : Méthode consistant à comparer deux versions d'une page ou d'une annonce pour identifier la plus performante statistiquement.
                    * **Churn Rate (Taux d'attrition)** : Pourcentage de clients ou d'abonnés perdus sur une période donnée.
                    * **Retargeting (Reciblage)** : Technique consistant à diffuser des publicités ciblées aux internautes ayant déjà visité votre site sans convertir.
                """.trimIndent(),
                contentBodyEn = """
                    ### 📖 Essential Growth Marketing Dictionary

                    Clear formulas and benchmarks for ROAS, CAC, LTV, CTR, CVR, CPC, and churn.
                """.trimIndent()
            )
        )
    }
}
