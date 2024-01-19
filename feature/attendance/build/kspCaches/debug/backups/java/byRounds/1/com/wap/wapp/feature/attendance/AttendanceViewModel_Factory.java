package com.wap.wapp.feature.attendance;

import com.wap.wapp.core.domain.usecase.event.GetDateEventListUseCase;
import com.wap.wapp.core.domain.usecase.user.GetUserRoleUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AttendanceViewModel_Factory implements Factory<AttendanceViewModel> {
  private final Provider<GetDateEventListUseCase> getDateEventListUseCaseProvider;

  private final Provider<GetUserRoleUseCase> getUserRoleUseCaseProvider;

  public AttendanceViewModel_Factory(
      Provider<GetDateEventListUseCase> getDateEventListUseCaseProvider,
      Provider<GetUserRoleUseCase> getUserRoleUseCaseProvider) {
    this.getDateEventListUseCaseProvider = getDateEventListUseCaseProvider;
    this.getUserRoleUseCaseProvider = getUserRoleUseCaseProvider;
  }

  @Override
  public AttendanceViewModel get() {
    return newInstance(getDateEventListUseCaseProvider.get(), getUserRoleUseCaseProvider.get());
  }

  public static AttendanceViewModel_Factory create(
      Provider<GetDateEventListUseCase> getDateEventListUseCaseProvider,
      Provider<GetUserRoleUseCase> getUserRoleUseCaseProvider) {
    return new AttendanceViewModel_Factory(getDateEventListUseCaseProvider, getUserRoleUseCaseProvider);
  }

  public static AttendanceViewModel newInstance(GetDateEventListUseCase getDateEventListUseCase,
      GetUserRoleUseCase getUserRoleUseCase) {
    return new AttendanceViewModel(getDateEventListUseCase, getUserRoleUseCase);
  }
}
