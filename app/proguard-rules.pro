-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
# App Models
-keep class dev.fsm.presentation.model.**
# App Utils
-keep class dev.fsm.utils.**
-keep class dev.fsm.presentation.screens.**
# Domain Models
-keep class dev.fsm.domain.models.**
# Domain Utils
-keep class dev.fsm.domain.utils.**

-keep class dev.fsm.data.storage.models.**
-keep class dev.fsm.data.network.api.models.**
-keep class dev.fsm.data.database.entities.**
-keep class dev.fsm.data.utils.**

# Parceler
-keep class **$$Parcelable { *; }

# OkHttp platform used only on JVM and when Conscrypt and other security providers are available.
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
