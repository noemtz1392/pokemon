object Dependencies {

    val app = listOf(
        Dependency.Startup,
        Dependency.Timber,
        Dependency.Hilt,
        Dependency.Ksp.Hilt,
        Dependency.HiltNavigationCompose,
        Dependency.Ksp.AndroidHilt
    )

    val baseData = listOf(
        Dependency.Room,
        Dependency.RoomKtk,
        Dependency.RoomPaging,
        Dependency.Ksp.Room,
    )

    val data = listOf(
        Dependency.Coroutines,
        Dependency.DateTime,
        Dependency.Hilt,
        Dependency.Ksp.Hilt,
        Dependency.OkHttp,
        Dependency.OkHttpLogging,
        Dependency.Retrofit,
        Dependency.RetrofitMoshiConverter,
        Dependency.Moshi,
        Dependency.MoshiAdapter,
        Dependency.Ksp.MoshiCodegen,
        Dependency.Timber,
    )

    val domain = listOf(Dependency.Coroutines)

    val di = listOf(
        Dependency.Dagger,
        Dependency.Ksp.Dagger
    )

    val presentation = listOf(
        Dependency.HiltNavigationCompose,
        Dependency.Hilt,
        Dependency.Ksp.Hilt,
        Dependency.Timber
    )
}