package consoleWork;

public class ScriptScanner implements Scanner {

    private String script;
    private int counter = 0;

    public ScriptScanner(String script) {
        this.script = script;
    }

    public String read() {
       try {
           String subString = script.split("\n")[counter];
           counter++;
            return subString;
       }catch (Exception e){
           return null;
       }
    }

    public void write(String srt) {
        System.out.println(srt);
    }
}
