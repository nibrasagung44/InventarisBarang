# Walkthrough - Resolved Room KSP2 "unexpected jvm signature V" Error

I have resolved the `unexpected jvm signature V` error by upgrading the Room database library and enabling the KSP2 processor, which handles Kotlin suspending functions correctly.

## Changes

### Build Configuration

#### [libs.versions.toml](file:///C:/Users/Muhammad%20Nibras%20A.%20A/Documents/Pemrograman%20Mobile/MyInventarisBarang/gradle/libs.versions.toml)
Upgraded Room from `2.6.1` to `2.8.4` and KSP to `2.0.21-1.0.28`.

```diff
 [versions]
-room = "2.6.1"
-ksp = "2.0.21-1.0.26"
+room = "2.8.4"
+ksp = "2.0.21-1.0.28"
```

#### [gradle.properties](file:///C:/Users/Muhammad%20Nibras%20A.%20A/Documents/Pemrograman%20Mobile/MyInventarisBarang/gradle.properties)
Enabled KSP2 to ensure compatibility with Room 2.8.4's improved signature handling.

```diff
 kotlin.code.style=official
 android.disallowKotlinSourceSets=false
+ksp.useKSP2=true
```

## Verification Results

### Automated Tests
- Ran `:app:kspDebugKotlin`: **PASSED**
- Ran `assembleDebug`: **PASSED**

> [!TIP]
> Room 2.8.4 includes better support for Kotlin Multiplatform and improved KSP2 stability. Using `ksp.useKSP2=true` is recommended for all modern projects using Kotlin 2.0+ to benefit from faster build times and better error reporting.
