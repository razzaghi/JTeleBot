package com.jamejam.api;

public interface AbstractChatContextFactory {

    ChatContext createChatContext(int chatId, TelegramBot bot);

}
