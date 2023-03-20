package ru.alrosa.transport.gastransportstatistics.entity.enums;

public enum TaskStatus {
    OPEN("Новая задача", "Постановщик задачи создает задачу в таск-трекере, указывает приоритет и описывает бизнес-требования"),
    READY_FOR_DESIGN("Готова к проработке", "Задача на этом этапе ожидает проработки/декомпозиции."),
    IN_DESIGN("В проработке", "Выполняется декомпозиция задачи (задача описывается так, чтобы ее понял человек который будет выполнять задачу, если нужно задача разбивается на подзадачи."),
    DESIGN_CHECK("Проверка выполненной проработки", "На данном этапе проверяется полнота требований, и все ли необходимые материалы приложены к задаче. В этом статусе задачей занимается человек, находящийся на более вышестоящей должности чем человек, который занимался задачей в статусе IN DESIGN. Например для Разработчика это будет Тимлид, а для Тимлида это будет вышестоящий Руководитель. Примечание: данный статус может быть пропущен, если вышестоящего Руководителя нет, или в компании доверяют человеку, который выполнял задачу в статусе IN DESIGN)."),
    READY_FOR_DEV("Задача готова для начала разработки", "Задача на этом этапе готова к разработке (при необходимости декомпозирована на Task-и и приоритеты расставлены, конечные исполнители могут быть назначены) Проверена полнота требований, все необходимые материалы приложены."),
    IN_DEV("Поступила в разработку", "Реализация задачи. Статус проставляется, когда конечный исполнитель начинает работу над Task."),
    READY_TO_REVIEW("Готова к поверхностному осмотру", "Задача ожидает, что ее возьмет в ревью человек, находящийся на более вышестоящей должности (по согласованию ревью может проводить человек занимающий аналогичную должность)"),
    IN_REVIEW("Выполняется поверхностный осмотр выполненной задачи", "Перевод задачи в этот статус означает готовность к поверхностной проверке (иногда к проверке качества) выполнения задачи.Это может быть как Review кода задачи, так и проверка написанной документации, тестов и т.п."),
    READY_FOR_TESTING("Задача готова к тестированию", "Выставляется когда проведено Review и отсутствуют замечания. CI должен быть пройден."),
    IN_TESTING("Задача в тестировании", "Выставляется когда QA начинает ручное/авто-тестирование задачи."),
    READY_FOR_DEPLOY("Задача готова к выкладке", "Выставляется когда QA провел тестирование."),
    ON_PROD("Задача на проде", "Выставляет релиз-менеджером, когда задача попала на Prod."),
    CLOSED("Задача выполнена", "Выставляется Project Manager-ом, когда задача на проде и удовлетворяет бизнес-требованиям."),
    WAIT_FOR_DEMO("Ожидает демонстрации", "Задача выполнена, выложена на test-сервер и готова к проведению демо. После успешного тестирования, задача готова к деплою на один из конечных серверов, например: production или beta (в зависимости от типа задачи). После проведения демо по задаче возможно создание ряда доработок (заведение Task-ов)."),
    READY_TO_CONTINUE("Задача готова к продолжению разработки", "Выставляется когда по задаче нужно внести исправления или появилась более приоритетная задача. Часто критичный баг на проде является причиной перевода текущей задачи в этот статус."),
    ON_HOLD("Когда не возможно перевести задачу на следующую стадию", "Если по задаче возникли вопросы, тормозящие выполнение задачи, задача переводится в статус 'On Hold'. В задаче оставляется обязательный комментарий в чем именно проблема с выполнением и она переводится на того человека/отдел, который может предоставить недостающую информацию или оказать другую помощь в решении. Когда человек ответил на вопрос - статус не меняется, но задача переводится на человека, который задавал вопрос."),
    REJECTED("Отклонена", "Когда задачу более не нужно выполнять (используется вместо удаления задачи).");

    private final String translateForRus;
    private final String explanation;

    TaskStatus(String translateForRus, String explanation) {
        this.translateForRus = translateForRus;
        this.explanation = explanation;
    }

    public String getTranslateForRus() {
        return translateForRus;
    }

    public String getExplanation() {
        return explanation;
    }
}
