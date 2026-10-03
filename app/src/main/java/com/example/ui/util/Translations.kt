package com.example.ui.util

object Translations {
    private val translations = mapOf(
        // Navigation & App Bar
        "nav_dashboard" to mapOf(
            AppLanguage.ENGLISH to "Desk",
            AppLanguage.HINDI to "डैशबोर्ड",
            AppLanguage.MARATHI to "डैशबोर्ड",
            AppLanguage.GUJARATI to "ડેશબોર્ડ"
        ),
        "nav_deliveries" to mapOf(
            AppLanguage.ENGLISH to "Dispatch",
            AppLanguage.HINDI to "डिलिवरी",
            AppLanguage.MARATHI to "डिलिव्हरी",
            AppLanguage.GUJARATI to "ડિલિવરી"
        ),
        "nav_customers" to mapOf(
            AppLanguage.ENGLISH to "Clients",
            AppLanguage.HINDI to "ग्राहक",
            AppLanguage.MARATHI to "ग्राहक",
            AppLanguage.GUJARATI to "ગ્રાહક"
        ),
        "nav_payments" to mapOf(
            AppLanguage.ENGLISH to "Ledger",
            AppLanguage.HINDI to "पेमेंट",
            AppLanguage.MARATHI to "पेमेंट",
            AppLanguage.GUJARATI to "ચુકવણી"
        ),
        "nav_plans" to mapOf(
            AppLanguage.ENGLISH to "Plans",
            AppLanguage.HINDI to "प्लान्स",
            AppLanguage.MARATHI to "प्लॅन्स",
            AppLanguage.GUJARATI to "પ્લાન"
        ),
        "title_crm" to mapOf(
            AppLanguage.ENGLISH to "Tiffin Service Manager",
            AppLanguage.HINDI to "टिफिन सेवा प्रबंधन",
            AppLanguage.MARATHI to "टिफिन सेवा व्यवस्थापन",
            AppLanguage.GUJARATI to "ટિફિન સેવા મેનેજમેન્ટ"
        ),
        "title_dispatch" to mapOf(
            AppLanguage.ENGLISH to "Daily Dispatch Sheet",
            AppLanguage.HINDI to "दैनिक डिलीवरी सूची",
            AppLanguage.MARATHI to "दैनिक डिलिव्हरी यादी",
            AppLanguage.GUJARATI to "દૈનિક ડિલિવરી યાદી"
        ),
        "title_directory" to mapOf(
            AppLanguage.ENGLISH to "Customer Directory",
            AppLanguage.HINDI to "ग्राहक सूची",
            AppLanguage.MARATHI to "ग्राहक यादी",
            AppLanguage.GUJARATI to "ગ્રાહક યાદી"
        ),
        "title_ledger" to mapOf(
            AppLanguage.ENGLISH to "Payment Ledger",
            AppLanguage.HINDI to "भुगतान खाता",
            AppLanguage.MARATHI to "पेमेंट रजिस्टर",
            AppLanguage.GUJARATI to "ચુકવણી રજિસ્ટર"
        ),
        "title_plans" to mapOf(
            AppLanguage.ENGLISH to "Meal Plans",
            AppLanguage.HINDI to "टिफिन प्लान्स",
            AppLanguage.MARATHI to "टिफिन प्लॅन्स",
            AppLanguage.GUJARATI to "ટિફિન પ્લાન"
        ),
        "title_customer_details" to mapOf(
            AppLanguage.ENGLISH to "Customer Profile",
            AppLanguage.HINDI to "ग्राहक प्रोफाइल",
            AppLanguage.MARATHI to "ग्राहक प्रोफाईल",
            AppLanguage.GUJARATI to "ગ્રાહક પ્રોફાઇલ"
        ),

        // Dashboard
        "dashboard_welcome" to mapOf(
            AppLanguage.ENGLISH to "Daily Kitchen Overview",
            AppLanguage.HINDI to "दैनिक रसोई विवरण",
            AppLanguage.MARATHI to "दैनिक टिफिन तपशील",
            AppLanguage.GUJARATI to "દૈનિક રસોડા ની વિગત"
        ),
        "today_summary" to mapOf(
            AppLanguage.ENGLISH to "Today's Delivery Status",
            AppLanguage.HINDI to "आज की डिलीवरी स्थिति",
            AppLanguage.MARATHI to "आजची डिलिव्हरी स्थिती",
            AppLanguage.GUJARATI to "આજની ડિલિવરી પરિસ્થિતિ"
        ),
        "total_customers" to mapOf(
            AppLanguage.ENGLISH to "Total Clients",
            AppLanguage.HINDI to "कुल ग्राहक",
            AppLanguage.MARATHI to "एकूण ग्राहक",
            AppLanguage.GUJARATI to "કુલ ગ્રાહકો"
        ),
        "today_deliveries" to mapOf(
            AppLanguage.ENGLISH to "Today's Tiffins",
            AppLanguage.HINDI to "आज के टिफिन",
            AppLanguage.MARATHI to "आजचे टिफिन",
            AppLanguage.GUJARATI to "આજના ટિફિન"
        ),
        "delivered" to mapOf(
            AppLanguage.ENGLISH to "Delivered",
            AppLanguage.HINDI to "वितरित (Delivered)",
            AppLanguage.MARATHI to "वितरित (Delivered)",
            AppLanguage.GUJARATI to "ડિલિવર (Delivered)"
        ),
        "pending" to mapOf(
            AppLanguage.ENGLISH to "Pending",
            AppLanguage.HINDI to "बकाया (Pending)",
            AppLanguage.MARATHI to "प्रलंबित (Pending)",
            AppLanguage.GUJARATI to "બાકી (Pending)"
        ),
        "skipped" to mapOf(
            AppLanguage.ENGLISH to "Skipped",
            AppLanguage.HINDI to "स्किप (Skipped)",
            AppLanguage.MARATHI to "स्किप (Skipped)",
            AppLanguage.GUJARATI to "રદ કરેલ (Skipped)"
        ),
        "total_collected" to mapOf(
            AppLanguage.ENGLISH to "Collected Dues",
            AppLanguage.HINDI to "प्राप्त राशि",
            AppLanguage.MARATHI to "जमा रक्कम",
            AppLanguage.GUJARATI to "મળેલ રકમ"
        ),
        "pending_payments" to mapOf(
            AppLanguage.ENGLISH to "Pending Dues",
            AppLanguage.HINDI to "बकाया राशि",
            AppLanguage.MARATHI to "एकूण थकबाकी",
            AppLanguage.GUJARATI to "બાકી રકમ"
        ),
        "quick_actions" to mapOf(
            AppLanguage.ENGLISH to "Quick Operations",
            AppLanguage.HINDI to "त्वरित कार्य",
            AppLanguage.MARATHI to "जलद कृती",
            AppLanguage.GUJARATI to "ઝડપી કાર્યો"
        ),
        "action_dispatch" to mapOf(
            AppLanguage.ENGLISH to "Dispatch Sheet",
            AppLanguage.HINDI to "डिलीवरी लिस्ट",
            AppLanguage.MARATHI to "डिलिव्हरी लिस्ट",
            AppLanguage.GUJARATI to "ડિલિવરી લિસ્ટ"
        ),
        "action_add_customer" to mapOf(
            AppLanguage.ENGLISH to "Add Customer",
            AppLanguage.HINDI to "नया ग्राहक",
            AppLanguage.MARATHI to "नवीन ग्राहक",
            AppLanguage.GUJARATI to "નવો ગ્રાહક"
        ),
        "action_record_payment" to mapOf(
            AppLanguage.ENGLISH to "Record Payment",
            AppLanguage.HINDI to "भुगतान दर्ज करें",
            AppLanguage.MARATHI to "पेमेंट नोंदवा",
            AppLanguage.GUJARATI to "ચુકવણી નોંધો"
        ),
        "action_add_plan" to mapOf(
            AppLanguage.ENGLISH to "Meal Plans",
            AppLanguage.HINDI to "मील प्लान्स",
            AppLanguage.MARATHI to "मील प्लॅन्स",
            AppLanguage.GUJARATI to "મીલ પ્લાન"
        ),
        "recent_deliveries" to mapOf(
            AppLanguage.ENGLISH to "Recent Deliveries",
            AppLanguage.HINDI to "हाल की डिलीवरी",
            AppLanguage.MARATHI to "अलीकडील डिलिव्हरी",
            AppLanguage.GUJARATI to "તાજેતરની ડિલિવરી"
        ),
        "unpaid_alerts" to mapOf(
            AppLanguage.ENGLISH to "Payment Alerts",
            AppLanguage.HINDI to "बकाया राशि चेतावनी",
            AppLanguage.MARATHI to "थकबाकी इशारा",
            AppLanguage.GUJARATI to "બાકી રકમ ચેતવણી"
        ),
        "view_all" to mapOf(
            AppLanguage.ENGLISH to "View All",
            AppLanguage.HINDI to "सभी देखें",
            AppLanguage.MARATHI to "सर्व पहा",
            AppLanguage.GUJARATI to "બધું જુઓ"
        ),

        // Deliveries
        "select_date" to mapOf(
            AppLanguage.ENGLISH to "Date",
            AppLanguage.HINDI to "तारीख",
            AppLanguage.MARATHI to "तारीख",
            AppLanguage.GUJARATI to "તારીખ"
        ),
        "shift_all" to mapOf(
            AppLanguage.ENGLISH to "All Time",
            AppLanguage.HINDI to "सभी समय",
            AppLanguage.MARATHI to "सर्व वेळ",
            AppLanguage.GUJARATI to "બધા સમય"
        ),
        "shift_lunch" to mapOf(
            AppLanguage.ENGLISH to "Lunch ☀️",
            AppLanguage.HINDI to "दोपहर ☀️",
            AppLanguage.MARATHI to "दुपार ☀️",
            AppLanguage.GUJARATI to "બપોરે ☀️"
        ),
        "shift_dinner" to mapOf(
            AppLanguage.ENGLISH to "Dinner 🌙",
            AppLanguage.HINDI to "रात 🌙",
            AppLanguage.MARATHI to "रात्र 🌙",
            AppLanguage.GUJARATI to "રાત્રે 🌙"
        ),
        "diet_all" to mapOf(
            AppLanguage.ENGLISH to "All Food",
            AppLanguage.HINDI to "सभी भोजन",
            AppLanguage.MARATHI to "सर्व आहार",
            AppLanguage.GUJARATI to "બધા ખોરાક"
        ),
        "diet_veg" to mapOf(
            AppLanguage.ENGLISH to "Veg 🟢",
            AppLanguage.HINDI to "शाकाहारी 🟢",
            AppLanguage.MARATHI to "शाकाहारी 🟢",
            AppLanguage.GUJARATI to "શાકાહારી 🟢"
        ),
        "diet_nonveg" to mapOf(
            AppLanguage.ENGLISH to "Non-Veg 🔴",
            AppLanguage.HINDI to "मांसाहारी 🔴",
            AppLanguage.MARATHI to "मांसाहारी 🔴",
            AppLanguage.GUJARATI to "માંસાહારી 🔴"
        ),
        "diet_jain" to mapOf(
            AppLanguage.ENGLISH to "Jain 🟣",
            AppLanguage.HINDI to "जैन 🟣",
            AppLanguage.MARATHI to "जैन 🟣",
            AppLanguage.GUJARATI to "જૈન 🟣"
        ),
        "status_all" to mapOf(
            AppLanguage.ENGLISH to "All Status",
            AppLanguage.HINDI to "सभी स्थिति",
            AppLanguage.MARATHI to "सर्व स्थिती",
            AppLanguage.GUJARATI to "બધી સ્થિતિ"
        ),
        "mark_delivered" to mapOf(
            AppLanguage.ENGLISH to "Delivered",
            AppLanguage.HINDI to "वितरित करें",
            AppLanguage.MARATHI to "वितरित करा",
            AppLanguage.GUJARATI to "ડિલિવર કરો"
        ),
        "mark_skipped" to mapOf(
            AppLanguage.ENGLISH to "Skip",
            AppLanguage.HINDI to "स्किप करें",
            AppLanguage.MARATHI to "स्किप करा",
            AppLanguage.GUJARATI to "રદ કરો"
        ),
        "mark_pending" to mapOf(
            AppLanguage.ENGLISH to "Pending",
            AppLanguage.HINDI to "बकाया करें",
            AppLanguage.MARATHI to "प्रलंबित करा",
            AppLanguage.GUJARATI to "બાકી કરો"
        ),
        "call_customer" to mapOf(
            AppLanguage.ENGLISH to "Call",
            AppLanguage.HINDI to "कॉल करें",
            AppLanguage.MARATHI to "कॉल करा",
            AppLanguage.GUJARATI to "કૉલ કરો"
        ),
        "whatsapp_msg" to mapOf(
            AppLanguage.ENGLISH to "WhatsApp",
            AppLanguage.HINDI to "व्हाट्सएप",
            AppLanguage.MARATHI to "व्हॉट्सॲप",
            AppLanguage.GUJARATI to "વોટ્સએપ"
        ),
        "no_deliveries" to mapOf(
            AppLanguage.ENGLISH to "No deliveries for selected filters.",
            AppLanguage.HINDI to "चुने गए फ़िल्टर के लिए कोई डिलीवरी नहीं मिली।",
            AppLanguage.MARATHI to "निवडलेल्या फिल्टरसाठी कोणतीही डिलिव्हरी सापडली नाही.",
            AppLanguage.GUJARATI to "પસંદ કરેલ ફિલ્ટર માટે કોઈ ડિલિવરી મળી નથી."
        ),

        // Customer & Directory
        "search_customer" to mapOf(
            AppLanguage.ENGLISH to "Search by name, phone or area...",
            AppLanguage.HINDI to "नाम, फोन या क्षेत्र से खोजें...",
            AppLanguage.MARATHI to "नाव, फोन किंवा परिसराने शोधा...",
            AppLanguage.GUJARATI to "નામ, ફોન અથવા વિસ્તાર દ્વારા શોધો..."
        ),
        "add_customer" to mapOf(
            AppLanguage.ENGLISH to "+ Add Customer",
            AppLanguage.HINDI to "+ नया ग्राहक जोड़ें",
            AppLanguage.MARATHI to "+ नवीन ग्राहक जोडा",
            AppLanguage.GUJARATI to "+ નવો ગ્રાહક ઉમેરો"
        ),
        "phone" to mapOf(
            AppLanguage.ENGLISH to "Phone Number",
            AppLanguage.HINDI to "फोन नंबर",
            AppLanguage.MARATHI to "फोन नंबर",
            AppLanguage.GUJARATI to "ફોન નંબર"
        ),
        "area" to mapOf(
            AppLanguage.ENGLISH to "Area / Society",
            AppLanguage.HINDI to "क्षेत्र / सोसाइटी",
            AppLanguage.MARATHI to "परिसर / सोसायटी",
            AppLanguage.GUJARATI to "વિસ્તાર / સોસાયટી"
        ),
        "address" to mapOf(
            AppLanguage.ENGLISH to "Full Address",
            AppLanguage.HINDI to "पूरा पता",
            AppLanguage.MARATHI to "पूर्ण पत्ता",
            AppLanguage.GUJARATI to "પૂરૂં સરનામું"
        ),
        "notes" to mapOf(
            AppLanguage.ENGLISH to "Delivery Notes",
            AppLanguage.HINDI to "विशेष निर्देश",
            AppLanguage.MARATHI to "विशेष सूचना",
            AppLanguage.GUJARATI to "ખાસ સૂચના"
        ),
        "dietary_pref" to mapOf(
            AppLanguage.ENGLISH to "Dietary Preference",
            AppLanguage.HINDI to "भोजन प्राथमिकता",
            AppLanguage.MARATHI to "आहार प्राधान्य",
            AppLanguage.GUJARATI to "ખોરાકની પસંદગી"
        ),
        "active_subs" to mapOf(
            AppLanguage.ENGLISH to "Active Subscriptions",
            AppLanguage.HINDI to "सक्रिय प्लान्स",
            AppLanguage.MARATHI to "सक्रिय प्लॅन",
            AppLanguage.GUJARATI to "સક્રિય પ્લાન"
        ),
        "paused" to mapOf(
            AppLanguage.ENGLISH to "Paused",
            AppLanguage.HINDI to "रोका हुआ",
            AppLanguage.MARATHI to "थांबवले",
            AppLanguage.GUJARATI to "અટકાવેલ"
        ),
        "active" to mapOf(
            AppLanguage.ENGLISH to "Active",
            AppLanguage.HINDI to "सक्रिय",
            AppLanguage.MARATHI to "सक्रिय",
            AppLanguage.GUJARATI to "સક્રિય"
        ),
        "edit" to mapOf(
            AppLanguage.ENGLISH to "Edit",
            AppLanguage.HINDI to "संपादित करें",
            AppLanguage.MARATHI to "संपादित करा",
            AppLanguage.GUJARATI to "ફેરફાર કરો"
        ),
        "delete" to mapOf(
            AppLanguage.ENGLISH to "Delete",
            AppLanguage.HINDI to "हटाएं",
            AppLanguage.MARATHI to "हटवा",
            AppLanguage.GUJARATI to "હટાવો"
        ),

        // Details
        "add_subscription" to mapOf(
            AppLanguage.ENGLISH to "+ Add Subscription",
            AppLanguage.HINDI to "+ नया प्लान जोड़ें",
            AppLanguage.MARATHI to "+ नवीन प्लॅन जोडा",
            AppLanguage.GUJARATI to "+ નવું સબ્સ્ક્રિપ્શન ઉમેરો"
        ),
        "pause_tiffin" to mapOf(
            AppLanguage.ENGLISH to "Pause Tiffin",
            AppLanguage.HINDI to "टिफिन रोकें",
            AppLanguage.MARATHI to "टिफिन थांबवा",
            AppLanguage.GUJARATI to "ટિફિન અટકાવો"
        ),
        "record_payment" to mapOf(
            AppLanguage.ENGLISH to "Record Payment",
            AppLanguage.HINDI to "भुगतान दर्ज करें",
            AppLanguage.MARATHI to "पेमेंट नोंदवा",
            AppLanguage.GUJARATI to "ચુકવણી નોંધો"
        ),
        "delivery_history" to mapOf(
            AppLanguage.ENGLISH to "Delivery History",
            AppLanguage.HINDI to "डिलीवरी इतिहास",
            AppLanguage.MARATHI to "डिलिव्हरी इतिहास",
            AppLanguage.GUJARATI to "ડિલિવરી ઇતિહાસ"
        ),
        "payment_history" to mapOf(
            AppLanguage.ENGLISH to "Payment History",
            AppLanguage.HINDI to "भुगतान इतिहास",
            AppLanguage.MARATHI to "पेमेंट इतिहास",
            AppLanguage.GUJARATI to "ચુકવણી ઇતિહાસ"
        ),
        "pause_schedules" to mapOf(
            AppLanguage.ENGLISH to "Pause Schedules",
            AppLanguage.HINDI to "रोकने का शेड्यूल",
            AppLanguage.MARATHI to "थांबवण्याचे वेळापत्रक",
            AppLanguage.GUJARATI to "અટકાવવાનું શેડ્યૂલ"
        ),
        "send_bill" to mapOf(
            AppLanguage.ENGLISH to "Send WhatsApp Bill",
            AppLanguage.HINDI to "व्हाट्सएप बिल भेजें",
            AppLanguage.MARATHI to "व्हॉट्सॲप बिल पाठवा",
            AppLanguage.GUJARATI to "વોટ્સએપ બિલ મોકલો"
        ),

        // Ledger & Payments
        "outstanding_title" to mapOf(
            AppLanguage.ENGLISH to "Outstanding Balance",
            AppLanguage.HINDI to "कुल बकाया राशि",
            AppLanguage.MARATHI to "एकूण थकबाकी",
            AppLanguage.GUJARATI to "કુલ બાકી રકમ"
        ),
        "overdue_customers" to mapOf(
            AppLanguage.ENGLISH to "Overdue Accounts",
            AppLanguage.HINDI to "अतिदेय खाते",
            AppLanguage.MARATHI to "थकित खाती",
            AppLanguage.GUJARATI to "બાકી ખાતાઓ"
        ),
        "payment_method" to mapOf(
            AppLanguage.ENGLISH to "Payment Method",
            AppLanguage.HINDI to "भुगतान का प्रकार",
            AppLanguage.MARATHI to "पेमेंट पद्धत",
            AppLanguage.GUJARATI to "ચુકવણી પદ્ધતિ"
        ),
        "amount" to mapOf(
            AppLanguage.ENGLISH to "Amount (₹)",
            AppLanguage.HINDI to "राशि (₹)",
            AppLanguage.MARATHI to "रक्कम (₹)",
            AppLanguage.GUJARATI to "રકમ (₹)"
        ),
        "note" to mapOf(
            AppLanguage.ENGLISH to "Note / Reference",
            AppLanguage.HINDI to "टिप्पणी",
            AppLanguage.MARATHI to "टीप",
            AppLanguage.GUJARATI to "નોંધ"
        ),

        // Meal Plans
        "add_plan" to mapOf(
            AppLanguage.ENGLISH to "+ Create Meal Plan",
            AppLanguage.HINDI to "+ नया प्लान बनाएं",
            AppLanguage.MARATHI to "+ नवीन प्लॅन तयार करा",
            AppLanguage.GUJARATI to "+ નવો પ્લાન બનાવો"
        ),
        "price_per_meal" to mapOf(
            AppLanguage.ENGLISH to "Price",
            AppLanguage.HINDI to "मूल्य",
            AppLanguage.MARATHI to "किंमत",
            AppLanguage.GUJARATI to "કિંમત"
        ),
        "total_meals" to mapOf(
            AppLanguage.ENGLISH to "Total Meals",
            AppLanguage.HINDI to "कुल टिफिन",
            AppLanguage.MARATHI to "एकूण टिफिन",
            AppLanguage.GUJARATI to "કુલ ટિફિન"
        ),
        "duration" to mapOf(
            AppLanguage.ENGLISH to "Duration",
            AppLanguage.HINDI to "अवधि",
            AppLanguage.MARATHI to "कालावधी",
            AppLanguage.GUJARATI to "સમયગાળો"
        ),

        // Controls
        "save" to mapOf(
            AppLanguage.ENGLISH to "Save",
            AppLanguage.HINDI to "सहेजें",
            AppLanguage.MARATHI to "साठवा",
            AppLanguage.GUJARATI to "સાચવો"
        ),
        "cancel" to mapOf(
            AppLanguage.ENGLISH to "Cancel",
            AppLanguage.HINDI to "रद्द करें",
            AppLanguage.MARATHI to "रद्द करा",
            AppLanguage.GUJARATI to "રદ કરો"
        ),
        "confirm" to mapOf(
            AppLanguage.ENGLISH to "Confirm",
            AppLanguage.HINDI to "पुष्टि करें",
            AppLanguage.MARATHI to "खात्री करा",
            AppLanguage.GUJARATI to "કન્ફર્મ કરો"
        ),
        "language" to mapOf(
            AppLanguage.ENGLISH to "Language",
            AppLanguage.HINDI to "भाषा",
            AppLanguage.MARATHI to "भाषा",
            AppLanguage.GUJARATI to "ભાષા"
        ),
        "theme" to mapOf(
            AppLanguage.ENGLISH to "Theme",
            AppLanguage.HINDI to "थीम",
            AppLanguage.MARATHI to "थीम",
            AppLanguage.GUJARATI to "થીમ"
        )
    )

    fun get(key: String, language: AppLanguage): String {
        return translations[key]?.get(language)
            ?: translations[key]?.get(AppLanguage.ENGLISH)
            ?: key
    }
}

fun String.tr(language: AppLanguage): String {
    return Translations.get(this, language)
}
