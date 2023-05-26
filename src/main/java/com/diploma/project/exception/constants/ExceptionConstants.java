package com.diploma.project.exception.constants;

import com.diploma.project.exception.event.EventType;
import org.springframework.http.HttpStatus;

import static com.diploma.project.exception.event.EventType.ERROR;
import static org.springframework.http.HttpStatus.*;

public enum ExceptionConstants {

    RF001("Данное поле: %s должно быть заполнено", BAD_REQUEST, ERROR),
    RF002("Данное поле: %s не должно быть заполнено", BAD_REQUEST, ERROR),
    LU002("Формат файла не соответствует: %s", INTERNAL_SERVER_ERROR, ERROR),
    LV003("Такой ИИН уже существует в Cистеме: %s", BAD_REQUEST, ERROR),
    LV004("Пользователь с таким email уже активирован в системе", BAD_REQUEST, ERROR),
    LV005("Ваша заявка находится на рассмотрении", BAD_REQUEST, ERROR),
    IU004("Аккаунт не активен", BAD_REQUEST, ERROR),
    IUP05("Неверные Логин или Пароль", BAD_REQUEST, ERROR),
    IBF06("В данное поле можно ввести только числовое значение, содержащее 12 символов", BAD_REQUEST, ERROR),
    CE007("Укажите корпоративную почту", BAD_REQUEST, ERROR),
    PM008("Несоответствие пароля", BAD_REQUEST, ERROR),
    NF012("Данный объект : %s не найден в базе данных по данному критерию поиска: %s", BAD_REQUEST, ERROR),
    IA013("Невалиданый параметр", BAD_REQUEST, ERROR),
    IA014("Ошибка регистрации", INTERNAL_SERVER_ERROR, ERROR),
    IA015("Ошибка при восстановлении пароля", INTERNAL_SERVER_ERROR, ERROR),
    UT016("Файл не загружен", BAD_REQUEST, ERROR),
    UT017("Неверный формат файла", BAD_REQUEST, ERROR),
    TE018("Истек срок действия токена", UNAUTHORIZED, ERROR),
    ISE01("Ошибка создания %s : причина: %s", INTERNAL_SERVER_ERROR, ERROR),
    CPE19("Ошибка при смене пароля.", BAD_REQUEST, ERROR),
    UTE01("Неверный тип выгрузки: %s", BAD_REQUEST, ERROR),
    IU006("Ваша заявка на регистрацию отклонена", BAD_REQUEST, ERROR),
    PD020("Недостаточно прав для удаления", FORBIDDEN, ERROR);


    private final String templateMessage;
    private final HttpStatus STATUS;
    private final EventType type;

    ExceptionConstants(final String templateMessage, final HttpStatus httpStatus, EventType type) {
        this.templateMessage = templateMessage;
        this.STATUS = httpStatus;
        this.type = type;
    }

    public String getTemplateMessage() {
        return templateMessage;
    }

    public HttpStatus getHttpStatus() {
        return STATUS;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.getTemplateMessage();
    }
}
