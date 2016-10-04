package lanit.util;

import java.io.Serializable;
import java.util.Map;

/**
 * Класс для хранения результата выполнения серверный функций, которые кроме кода результата
 * возвращают сообщение с описанием результата.
 * 
 * @author belov_av
 */
public class ResultHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String MSG_KEY = "msg";
    public static final String RESULT_CODE_KEY = "result";

    private final String msg;
    private final int resultCode;

    /**
     * Создает ResultHolder с указанными кодом результата и сообщением.
     * 
     * @param msg сообщение с описанием результата
     * @param resultCode код результата
     */
    public ResultHolder(final String msg, final int resultCode ) {
        this.msg = msg;
        this.resultCode = resultCode;
    }

    /**
     * Создает ResultHolder на основе карты параметров вызова серверной процедуры. В карте должны
     * содержаться ключи "msg" и "result".
     * 
     * @param params карта параметров
     */
    public ResultHolder(final Map< String, Object > params ) {
        if ( !params.containsKey( MSG_KEY ) || !params.containsKey( RESULT_CODE_KEY ) ) {
            throw new IllegalArgumentException(
                    "Can't found key \"" + MSG_KEY + "\" or \"" + RESULT_CODE_KEY + "\" in map" );
        }
        this.msg = (String) params.get( MSG_KEY );
        this.resultCode = (Integer) params.get( RESULT_CODE_KEY );
    }

    /**
     * Возвращает сообщение с описанием результата.
     * 
     * @return сообщение
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Возвращает код результата.
     * 
     * @return код результата
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * Возвращает признак успешности выполнения действия.
     * 
     * @return - true/false
     */
    public boolean isSuccess() {
        return resultCode >= 0;
    }

}
