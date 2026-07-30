# Implementation Plan - Fix Room KSP2 "unexpected jvm signature V" Error

The project is failing to build with the error `[ksp] java.lang.IllegalStateException: unexpected jvm signature V`. This is a known issue when using Room with KSP2, specifically for `suspend` DAO methods that return `Unit`.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/Muhammad%20Nibras%20A.%20A/Documents/Pemrograman%20Mobile/MyInventarisBarang/gradle/libs.versions.toml)
- Update `room` version from `2.6.1` to `2.8.4`.
- Update `ksp` version to `2.0.21-1.0.28` (optional but recommended for Kotlin 2.0.21).

#### [MODIFY] [gradle.properties](file:///C:/Users/Muhammad%20Nibras%20A.%20A/Documents/Pemrograman%20Mobile/MyInventarisBarang/gradle.properties)
- Add `ksp.useKSP2=true` to explicitly enable KSP2, which is compatible with Room 2.8.4 and resolves the signature issue.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:kspDebugKotlin` to verify that KSP processing succeeds.
- Run `./gradlew assembleDebug` to ensure the entire project builds successfully.

### Manual Verification
- Verify that the Room database still functions correctly by running the app (if a device is available).
