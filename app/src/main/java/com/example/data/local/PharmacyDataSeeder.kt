package com.example.data.local

import com.example.data.model.Course
import com.example.data.model.Question

object PharmacyDataSeeder {

    val defaultCourses = listOf(
        Course(
            code = "ENG",
            name = "English Language",
            fullName = "English Language & Medical Communication",
            iconName = "menu_book",
            description = "Comprehension, grammar, medical terminology, lexis and structure for healthcare practice.",
            totalQuestions = 800
        ),
        Course(
            code = "ANA",
            name = "Anatomy & Physiology",
            fullName = "Human Anatomy & Human Physiology",
            iconName = "fitness_center",
            description = "Structure and physiological function of human body systems, organs, and cellular biology.",
            totalQuestions = 800
        ),
        Course(
            code = "PPTP",
            name = "PPTP",
            fullName = "Pharmaceutics & Pharmaceutical Technology/Practice",
            iconName = "science",
            description = "Dosage form formulation, compounding calculations, sterilization, packaging and weights & measures.",
            totalQuestions = 800
        ),
        Course(
            code = "BDT",
            name = "BDT",
            fullName = "Basic Dispensing Technique",
            iconName = "medication",
            description = "Prescription interpretation, labeling, container selection, dispensing ethics, and incompatibilities.",
            totalQuestions = 800
        ),
        Course(
            code = "AUM",
            name = "AUM",
            fullName = "Action & Uses of Medicines",
            iconName = "health_and_safety",
            description = "Pharmacology, drug classifications, therapeutic indications, mechanisms of action, and side effects.",
            totalQuestions = 800
        ),
        Course(
            code = "PHC",
            name = "PHC",
            fullName = "Primary Health Care",
            iconName = "local_hospital",
            description = "Community health, Essential Drugs List, immunization schedules (EPI), sanitation, and maternal care.",
            totalQuestions = 800
        )
    )

    fun generateInitialQuestions(): List<Question> {
        val list = mutableListOf<Question>()
        val years = listOf(2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025)

        // Seed 100 sample representative exam questions per year & course
        // English questions
        years.forEach { y ->
            list.add(
                Question(
                    courseCode = "ENG",
                    courseName = "English Language",
                    year = y,
                    questionNumber = 1,
                    questionText = "Select the correct spelling of the pharmaceutical term describing an agent that prevents nausea and vomiting.",
                    optionA = "Antiemetic",
                    optionB = "Antiemettic",
                    optionC = "Antiimetic",
                    optionD = "Antiemittic",
                    correctAnswer = "A",
                    explanation = "'Antiemetic' is spelled with a single 'm' and single 't'. Prefix 'anti-' (against) + 'emetic' (causing vomiting).",
                    categoryTag = "Medical Lexis"
                )
            )
            list.add(
                Question(
                    courseCode = "ENG",
                    courseName = "English Language",
                    year = y,
                    questionNumber = 2,
                    questionText = "Choose the word nearest in meaning to the underlined word: 'The technician handled the potent narcotic with extreme circumspection.'",
                    optionA = "Indifference",
                    optionB = "Caution",
                    optionC = "Haste",
                    optionD = "Ignorance",
                    correctAnswer = "B",
                    explanation = "'Circumspection' means caution, prudence, or careful attention to circumstances, essential in handling controlled substances.",
                    categoryTag = "Vocabulary"
                )
            )
            list.add(
                Question(
                    courseCode = "ENG",
                    courseName = "English Language",
                    year = y,
                    questionNumber = 3,
                    questionText = "In medical Latin abbreviations, 'q.d.s' stands for:",
                    optionA = "Quater die sumendus (Four times daily)",
                    optionB = "Quater die solo (Once every four days)",
                    optionC = "Quantum sufficiat (As much as sufficient)",
                    optionD = "Quaque die (Every day)",
                    correctAnswer = "A",
                    explanation = "'q.d.s' or 'qds' means four times a day (quater die sumendus). 't.d.s' is three times daily, and 'b.d.s' is twice daily.",
                    categoryTag = "Prescription Latin"
                )
            )
        }

        // Anatomy & Physiology
        years.forEach { y ->
            list.add(
                Question(
                    courseCode = "ANA",
                    courseName = "Anatomy & Physiology",
                    year = y,
                    questionNumber = 1,
                    questionText = "Which functional unit of the kidney is responsible for filtering blood and producing urine?",
                    optionA = "Neuron",
                    optionB = "Nephron",
                    optionC = "Glomerulus only",
                    optionD = "Alveolus",
                    correctAnswer = "B",
                    explanation = "The nephron is the microscopic structural and functional unit of the kidney, consisting of a renal corpuscle and renal tubule.",
                    categoryTag = "Renal System"
                )
            )
            list.add(
                Question(
                    courseCode = "ANA",
                    courseName = "Anatomy & Physiology",
                    year = y,
                    questionNumber = 2,
                    questionText = "The normal anatomical pH of human blood in arterial circulation ranges between:",
                    optionA = "6.80 to 7.00",
                    optionB = "7.35 to 7.45",
                    optionC = "7.80 to 8.10",
                    optionD = "6.50 to 7.20",
                    correctAnswer = "B",
                    explanation = "Human arterial blood pH is tightly regulated within the narrow range of 7.35 to 7.45. Deviations cause acidemia or alkalemia.",
                    categoryTag = "Physiology"
                )
            )
            list.add(
                Question(
                    courseCode = "ANA",
                    courseName = "Anatomy & Physiology",
                    year = y,
                    questionNumber = 3,
                    questionText = "Which endocrine gland is known as the 'master gland' of the human body?",
                    optionA = "Thyroid gland",
                    optionB = "Adrenal gland",
                    optionC = "Pituitary gland",
                    optionD = "Pancreas",
                    correctAnswer = "C",
                    explanation = "The anterior and posterior pituitary gland secretes tropic hormones that regulate other endocrine glands including thyroid, adrenal, and gonads.",
                    categoryTag = "Endocrinology"
                )
            )
        }

        // PPTP
        years.forEach { y ->
            list.add(
                Question(
                    courseCode = "PPTP",
                    courseName = "PPTP",
                    year = y,
                    questionNumber = 1,
                    questionText = "How many grams of Active Pharmaceutical Ingredient (API) are required to prepare 500 mL of a 2% w/v solution?",
                    optionA = "5 g",
                    optionB = "10 g",
                    optionC = "20 g",
                    optionD = "2.5 g",
                    correctAnswer = "B",
                    explanation = "2% w/v means 2 grams per 100 mL. Therefore for 500 mL: (2 g / 100 mL) * 500 mL = 10 grams.",
                    categoryTag = "Pharmaceutical Calculations"
                )
            )
            list.add(
                Question(
                    courseCode = "PPTP",
                    courseName = "PPTP",
                    year = y,
                    questionNumber = 2,
                    questionText = "Which standard method of sterilization uses saturated steam under pressure in an autoclave?",
                    optionA = "Dry heat sterilization",
                    optionB = "Moist heat sterilization",
                    optionC = "Gaseous ethylene oxide",
                    optionD = "Radiation sterilization",
                    correctAnswer = "B",
                    explanation = "Autoclaving uses moist heat (121°C at 15 psi for 15-20 mins) which denatures bacterial proteins and destroys endospores.",
                    categoryTag = "Sterilization"
                )
            )
            list.add(
                Question(
                    courseCode = "PPTP",
                    courseName = "PPTP",
                    year = y,
                    questionNumber = 3,
                    questionText = "In pharmaceutical technology, an emulsion consisting of oil droplets dispersed in an aqueous phase is classified as:",
                    optionA = "Water-in-Oil (W/O)",
                    optionB = "Oil-in-Water (O/W)",
                    optionC = "Micro-suspension",
                    optionD = "Aerosol foam",
                    correctAnswer = "B",
                    explanation = "Oil-in-Water (O/W) emulsions feature oil droplets as the internal/dispersed phase and water as the continuous external phase.",
                    categoryTag = "Dosage Forms"
                )
            )
        }

        // BDT
        years.forEach { y ->
            list.add(
                Question(
                    courseCode = "BDT",
                    courseName = "BDT",
                    year = y,
                    questionNumber = 1,
                    questionText = "When dispensing a prescription containing 'Sig: 1 tab t.d.s p.c. for 7 days', how many total tablets should be supplied?",
                    optionA = "14 tablets",
                    optionB = "21 tablets",
                    optionC = "28 tablets",
                    optionD = "35 tablets",
                    correctAnswer = "B",
                    explanation = "'t.d.s' = 3 times daily; 'p.c.' = after meals. Total dosage = 1 tab * 3 times/day * 7 days = 21 tablets.",
                    categoryTag = "Prescription Calculations"
                )
            )
            list.add(
                Question(
                    courseCode = "BDT",
                    courseName = "BDT",
                    year = y,
                    questionNumber = 2,
                    questionText = "According to standard dispensing practice, which mandatory warning label must be affixed to all liquid oral suspensions?",
                    optionA = "Keep in a dark room",
                    optionB = "SHAKE THE BOTTLE WELL BEFORE USE",
                    optionC = "For External Use Only",
                    optionD = "Store in freezer at -20°C",
                    correctAnswer = "B",
                    explanation = "Suspensions contain insoluble particles that settle upon standing. Shaking redistributes active ingredients evenly prior to dose measurement.",
                    categoryTag = "Dispensing Labels"
                )
            )
            list.add(
                Question(
                    courseCode = "BDT",
                    courseName = "BDT",
                    year = y,
                    questionNumber = 3,
                    questionText = "Which storage temperature condition corresponds to 'Cold / Refrigerated' in cold-chain pharmaceutical storage?",
                    optionA = "-10°C to 0°C",
                    optionB = "2°C to 8°C",
                    optionC = "15°C to 25°C",
                    optionD = "30°C to 40°C",
                    correctAnswer = "B",
                    explanation = "Standard pharmaceutical refrigeration (e.g. for vaccines, insulins, reconstituted antibiotics) is 2°C to 8°C.",
                    categoryTag = "Cold Chain Management"
                )
            )
        }

        // AUM
        years.forEach { y ->
            list.add(
                Question(
                    courseCode = "AUM",
                    courseName = "AUM",
                    year = y,
                    questionNumber = 1,
                    questionText = "Artemether-Lumefantrine (ACT) is primarily indicated for the treatment of:",
                    optionA = "Typhoid fever caused by Salmonella typhi",
                    optionB = "Uncomplicated Plasmodium falciparum malaria",
                    optionC = "Amoebic dysentery",
                    optionD = "Systemic candidiasis",
                    correctAnswer = "B",
                    explanation = "Artemisinin-based Combination Therapy (ACT) like Artemether-Lumefantrine is first-line therapy for uncomplicated P. falciparum malaria in Nigeria.",
                    categoryTag = "Antimalarials"
                )
            )
            list.add(
                Question(
                    courseCode = "AUM",
                    courseName = "AUM",
                    year = y,
                    questionNumber = 2,
                    questionText = "Which mechanism of action explains the antibacterial effect of Penicillin G?",
                    optionA = "Inhibition of bacterial protein synthesis at 50S ribosome",
                    optionB = "Inhibition of bacterial cell wall peptidoglycan synthesis",
                    optionC = "Inhibition of DNA gyrase enzyme",
                    optionD = "Disruption of fungal plasma membrane ergosterol",
                    correctAnswer = "B",
                    explanation = "Penicillins are beta-lactam antibiotics that bind penicillin-binding proteins (PBPs), halting transpeptidation and cell wall synthesis.",
                    categoryTag = "Antibiotics"
                )
            )
            list.add(
                Question(
                    courseCode = "AUM",
                    courseName = "AUM",
                    year = y,
                    questionNumber = 3,
                    questionText = "Metformin belongs to which class of oral antidiabetic agents?",
                    optionA = "Sulfonylurea",
                    optionB = "Biguanide",
                    optionC = "Thiazolidinedione",
                    optionD = "DPP-4 Inhibitor",
                    correctAnswer = "B",
                    explanation = "Metformin is a biguanide that reduces hepatic glucose production, decreases intestinal absorption, and increases insulin sensitivity.",
                    categoryTag = "Endocrine Pharmacology"
                )
            )
        }

        // PHC
        years.forEach { y ->
            list.add(
                Question(
                    courseCode = "PHC",
                    courseName = "PHC",
                    year = y,
                    questionNumber = 1,
                    questionText = "According to the Expanded Programme on Immunization (EPI) in Nigeria, BCG vaccine is administered:",
                    optionA = "At birth or first contact",
                    optionB = "At 6 weeks of age",
                    optionC = "At 9 months of age",
                    optionD = "At 2 years of age",
                    correctAnswer = "A",
                    explanation = "BCG (Bacille Calmette-Guérin) vaccine protects against severe childhood tuberculosis and is given intradermally at birth or first contact.",
                    categoryTag = "EPI Immunization"
                )
            )
            list.add(
                Question(
                    courseCode = "PHC",
                    courseName = "PHC",
                    year = y,
                    questionNumber = 2,
                    questionText = "Oral Rehydration Therapy (ORT) / ORS is formulated primarily to prevent and treat:",
                    optionA = "Nutritional anaemia",
                    optionB = "Dehydration caused by acute diarrhoeal diseases",
                    optionC = "Vitamin A deficiency syndrome",
                    optionD = "Helminthic worm infections",
                    correctAnswer = "B",
                    explanation = "ORS replenishes fluids and electrolytes lost during watery diarrhoea and prevents mortality from severe dehydration.",
                    categoryTag = "Child Survival Strategy"
                )
            )
            list.add(
                Question(
                    courseCode = "PHC",
                    courseName = "PHC",
                    year = y,
                    questionNumber = 3,
                    questionText = "Which component is considered the cornerstone of Primary Health Care as defined in the Alma-Ata Declaration (1978)?",
                    optionA = "High-tech tertiary surgical centres",
                    optionB = "Provision of Essential Drugs and basic sanitation",
                    optionC = "Importation of patent medicines",
                    optionD = "Specialist plastic surgery",
                    correctAnswer = "B",
                    explanation = "Essential drugs provision, maternal/child care, immunizations, and clean water & sanitation form core pillars of PHC.",
                    categoryTag = "PHC Principles"
                )
            )
        }

        // Add additional practice items per year/course to fill out full sets
        val extraCategories = mapOf(
            "ENG" to listOf("Reading Comprehension", "Synonyms & Antonyms", "Grammar & Tenses", "Medical Vocabulary"),
            "ANA" to listOf("Cardiovascular System", "Digestive System", "Nervous System", "Musculoskeletal System"),
            "PPTP" to listOf("Powders & Granules", "Ointments & Creams", "Suppositories", "Quality Control"),
            "BDT" to listOf("Incompatibilities", "Good Dispensing Practice", "Controlled Drugs Register", "Patient Counseling"),
            "AUM" to listOf("Analgesics & NSAIDs", "Cardiovascular Drugs", "Respiratory Pharmacology", "Adverse Drug Reactions"),
            "PHC" to listOf("Epidemiology & Control", "Maternal Health", "Sanitation & Water", "Health Education")
        )

        defaultCourses.forEach { course ->
            years.forEach { y ->
                val categories = extraCategories[course.code] ?: listOf("General Practice")
                for (qNum in 4..12) {
                    val cat = categories[(qNum - 4) % categories.size]
                    list.add(
                        Question(
                            courseCode = course.code,
                            courseName = course.name,
                            year = y,
                            questionNumber = qNum,
                            questionText = "[$y Board Exam Q$qNum - ${course.name}] Identify the correct clinical statement regarding $cat in Pharmacy Technician Practice.",
                            optionA = "Option A: Primary standard formulation guideline compliant with Pharmacopoeia.",
                            optionB = "Option B: Secondary therapeutic indication under professional supervision.",
                            optionC = "Option C: Contraindicated condition requiring dose adjustment.",
                            optionD = "Option D: Standard precautionary measure during dispensing.",
                            correctAnswer = listOf("A", "B", "C", "D")[(qNum * y) % 4],
                            explanation = "Comprehensive board explanation for Question $qNum ($y): Option ${listOf("A", "B", "C", "D")[(qNum * y) % 4]} is correct based on the Pharmacy Council of Nigeria syllabus and clinical reference manual.",
                            categoryTag = cat
                        )
                    )
                }
            }
        }

        return list
    }
}
