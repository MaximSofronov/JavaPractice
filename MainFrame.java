import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import static java.lang.System.exit;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -4700133615545878302L;

    private String title; //титульник окна
    private Dimension dmnsn; // размер

    private JPanel mainPanel = new JPanel(new GridBagLayout()); // Главная панель на которой размещаются панели для кнопок и изображения
    private JPanel mainScreenPanel = new JPanel(new GridBagLayout()); //панель для отображения работы

    private Array array;
    private ArrayList<Step> arrayList;
    private int nextPrevIndex;


    UserPanel infoGathererPanel = new UserPanel();


    public MainFrame(String title, Dimension dmnsn){ //конструктор базового окна
        this.title = title;
        this.dmnsn = dmnsn;
    }

    public void init(){ // заполнение базового окна
        setTitle(title); // установка титула
        setSize(dmnsn); // задача размеров
        setLayout(new GridBagLayout()); //установка лейоута для размещения компонентов
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // при нажатии креста закрывает окно
        setLocationRelativeTo(null); // не понял
        setResizable(false); // нельзя разворачивать на фуллскрин и вообще менять размеры

        mainPanel.setPreferredSize(new Dimension(1000,700)); // ставит предпочитаемый размер основной панели
        mainPanel.setBorder(new EtchedBorder()); // делает обводку главной панели
        add(mainPanel); // добавляет панель на базовое окно

        mainScreenPanel.setBackground(Color.BLACK); // делает панель вывода алгоритма черной


        mainPanel.add(mainScreenPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH,
                new Insets(15,15,120,15), 0, 0));

        mainPanel.add(infoGathererPanel.getPanel(), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(0,15,15,15), 0, 0));

        infoGathererPanel.loadButton.addActionListener(new LoadButtonActionListener()); //привязка действий - "листенеров" к кнопкам
        infoGathererPanel.prevButton.addActionListener(new PrevButtonActionListener());
        infoGathererPanel.nextButton.addActionListener(new NextButtonActionListener());
        infoGathererPanel.startButton.addActionListener(new StartButtonActionListener());
        infoGathererPanel.resultButton.addActionListener(new ResultButtonActionListener());

        setVisible(true);
    }

    public class LoadButtonActionListener implements ActionListener{ //класс-листенер для кнопки load

        @Override
        public void actionPerformed(ActionEvent event){
        
            infoGathererPanel.txtFieldMinSize.setText(""); // сбрасывает текст из текстфилдов
            infoGathererPanel.txtFieldMaxSize.setText("");
            infoGathererPanel.txtFieldSize.setText("");
            infoGathererPanel.prevButton.setEnabled(false); // выключает кнопку prev, тк будет 0й шаг алгоритма и назад некуда
            infoGathererPanel.nextButton.setEnabled(true); //активирует кнопки для запуска алгоритма
            infoGathererPanel.startButton.setEnabled(true);
            infoGathererPanel.resultButton.setEnabled(true);
        }
    }

    public class PrevButtonActionListener implements ActionListener{ //класс-листенер для кнопки prev

        @Override
        public void actionPerformed(ActionEvent event){
            infoGathererPanel.resultButton.setEnabled(true);
            infoGathererPanel.nextButton.setEnabled(true);// включает кнопку некст тк был шаг назад значит можем и вперед

            if (nextPrevIndex==1){
                infoGathererPanel.prevButton.setEnabled(false);
            }
        }
    }

    public class NextButtonActionListener implements ActionListener{//класс-листенер для кнопки next

        @Override
        public void actionPerformed(ActionEvent event){
            infoGathererPanel.prevButton.setEnabled(true); // включает кнопку прев тк был шаг вперед значит можем и назад

            if (nextPrevIndex==(arrayList.size()-2)){
                infoGathererPanel.nextButton.setEnabled(false);
                infoGathererPanel.resultButton.setEnabled(false);
            }

        }
    }

    public class StartButtonActionListener implements ActionListener{//класс-листенер для кнопки start

        @Override
        public void actionPerformed(ActionEvent event){


            infoGathererPanel.prevButton.setEnabled(false);
            infoGathererPanel.loadButton.setEnabled(false); //выключает все кнопки чтобы пользователь не мог помешать работе отображения
            infoGathererPanel.nextButton.setEnabled(false);
            infoGathererPanel.startButton.setEnabled(false);
            infoGathererPanel.resultButton.setEnabled(false);




            for (nextPrevIndex = 0; nextPrevIndex < arrayList.size(); nextPrevIndex++) {
                GraphicsPanel gp2 = arrayList.get(nextPrevIndex).paint();

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                        

                nextPrevIndex--;
            }

            infoGathererPanel.loadButton.setEnabled(true); // возвращает все кнопки кроме некст тк последний шаг и дальше некуда                infoGathererPanel.prevButton.setEnabled(true);
            infoGathererPanel.startButton.setEnabled(true);
            
            


        }
    }

    public class ResultButtonActionListener implements ActionListener{//класс-листенер для кнопки result

        @Override
        public void actionPerformed(ActionEvent event){
            infoGathererPanel.prevButton.setEnabled(true); // активирует кнопку, на всякий случае если она была выключена
            infoGathererPanel.nextButton.setEnabled(false); // выключает нектс тк дальше некуда


            nextPrevIndex = arrayList.size()-1;

            infoGathererPanel.resultButton.setEnabled(false);
        }
    }

}