ALTER TABLE Person
    ADD COLUMN IF NOT EXISTS Role VARCHAR(50) NOT NULL DEFAULT 'USER';

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM Person WHERE email = 'wazewa@test.ru') THEN
            INSERT INTO Person(email, name, surname, hash_password) VALUES
                ('wazewa@test.ru', 'Иван', 'Королев', '$2a$10$31luSFqfVI404Ahc9AznjObrGrt./jZA/mV8bDgH5wU8A.SLlIp/C');
        END IF;
    END $$;

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM Landmark_Category WHERE landmark_category_id = 1) THEN
            INSERT INTO Landmark_Category(landmark_category_id, name) VALUES (1, 'Парк');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM Landmark_Category WHERE landmark_category_id = 2) THEN
            INSERT INTO Landmark_Category(landmark_category_id, name) VALUES (2, 'Памятник');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM Landmark_Category WHERE landmark_category_id = 3) THEN
            INSERT INTO Landmark_Category(landmark_category_id, name) VALUES (3, 'Музей');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM Landmark_Category WHERE landmark_category_id = 4) THEN
            INSERT INTO Landmark_Category(landmark_category_id, name) VALUES (4, 'Вокзал');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM Landmark_Category WHERE landmark_category_id = 5) THEN
            INSERT INTO Landmark_Category(landmark_category_id, name) VALUES (5, 'Собор');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM Landmark_Category WHERE landmark_category_id = 6) THEN
            INSERT INTO Landmark_Category(landmark_category_id, name) VALUES (6, 'Место отдыха');
        END IF;
    END $$;

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM Landmark WHERE name = 'Памятник В. И. Ленину') THEN
            INSERT INTO Landmark(name, description, coordinates, address, landmark_category_id)
            VALUES (
                       'Памятник В. И. Ленину',
                       'Первоначальное открытие памятника состоялось 9 июня 1946 года. Во второй раз памятник был установлен на площади им. 1 Мая летом 1957 года, в самом центре города.',
                       'POINT(53.9621449 48.7912533)',
                       'Площадь 1-го мая',
                       2
                   );
        END IF;

        IF NOT EXISTS (SELECT 1 FROM Landmark WHERE name = 'Памятник воинам, погибшим в годы Великой Отечественной войны') THEN
            INSERT INTO Landmark(name, description, coordinates, address, landmark_category_id)
            VALUES (
                       'Памятник воинам, погибшим в годы Великой Отечественной войны',
                       'Инициатива создания памятника 1116 землякам, погибшим в годы Великой Отечественной войны, принадлежала председателю Сенгилеевского райисполкома Сергею Михайловичу Вилкову. Скульптурная композиция, созданная Фивейским на Калужской фабрике, изображает двух бойцов смотрящих вдаль — пехотинца и моряка с автоматами. Высота памятника, установленного около пристани на берегу Волги, достигает 3,6 метра, вес 1 тонна, плинт 0,3 метра. Монумент установлен на железобетонной площадке со ступенями на две стороны. На площадке возвышается металлическая стела цилиндрической формы, а слева находится импровизированная трибуна. Открытие состоялось 9 мая 1975 года в день 30-летия победы советского народа над фашистской Германией.',
                       'POINT(53.963744 48.801596)',
                       'ул. Луначарского',
                       2
                   );
        END IF;

        IF NOT EXISTS (SELECT 1 FROM Landmark WHERE name = 'Памятник Г.Д. Гаю') THEN
            INSERT INTO Landmark(name, description, coordinates, address, landmark_category_id)
            VALUES (
                       'Памятник Г.Д. Гаю',
                       'Монумент с бюстом Г. Д. Гая (1887—1937) создан в 1967 году Николаем Александровичем Селивановым в честь освобождения Симбирска от белогвардейцев в сентябре 1918 года. На памятнике имеется мемориальная доска со словами «Гай. 1887—1937. Железным рыцарям — гаевцам, ушедшим отсюда в бой за революцию в бессмертие».',
                       'POINT(53.957201 48.798274)',
                       'ул. Гая, 70А',
                       2
                   );
        END IF;

        IF NOT EXISTS (SELECT 1 FROM Landmark WHERE name = 'Памятник Герою Советского Союза К. Пушкарёву') THEN
            INSERT INTO Landmark(name, description, coordinates, address, landmark_category_id)
            VALUES (
                       'Памятник Герою Советского Союза К. Пушкарёву',
                       'Монумент с бюстом героя К. И. Пушкарёва (1914—1938), погибшего у озера Хасан в горящем танке, открыт 7 ноября 1968 года. Надпись на мемориальной доске памятника гласит: «Пушкарёв Константин Иванович родился в 1915 г. в городе Сенгилее. Погиб в 1938 г. в боях у озера Хасан».',
                       'POINT(53.9611947 48.7963582)',
                       'ул. Пушкарева',
                       2
                   );
        END IF;

        IF NOT EXISTS (SELECT 1 FROM Landmark WHERE name = 'Районный краеведческий музей имени А. И. Солуянова') THEN
            INSERT INTO Landmark(name, description, coordinates, address, landmark_category_id)
            VALUES (
                       'Районный краеведческий музей имени А. И. Солуянова',
                       'В начале 1960-х годов коллектив активистов города, в который входили учителя, интеллигенция, любители истории, под руководством заведующего отделом пропаганды в РК КПСС Александра Солуянова, выдвинули идею о воссоздании в городе краеведческого музея. 23 февраля 1964 года общими усилиями в городе был открыт краеведческий музей — первый из негосударственных музеев Ульяновской области. Он разместился в одной из комнат Сенгилеевского педагогического училища и работал всего один раз в неделю по воскресеньям. Его первым директором стал Александр Солуянов. Затем музей был переведён в здание редакции районной газеты, где ему была выделена комната на площадке второго этажа. В 1967 году музею выделено новое здание на улице Ленина, 22, в котором до этого находился городской банк. В 1968 году краеведческий музей вновь открылся для посетителей. 21 апреля 1999 года музею было присвоено имя его основателя, Солуянова Александра Ивановича. В данный момент, здание музея располагается по адресу ул. Октябрьская, д.8',
                       'POINT(48.7988442 53.9664650)',
                       'ул. Октябрьская, д.8',
                       3
                   );
        END IF;

        IF NOT EXISTS (SELECT 1 FROM Landmark WHERE name = 'Речной вокзал') THEN
            INSERT INTO Landmark(name, description, coordinates, address, landmark_category_id)
            VALUES (
                       'Речной вокзал',
                       'Речное сообщение было прекращено в середине 90-х годов. В здании бывшего речного порта ныне расположен автовокзал. От него ходят рейсы в Ульяновск, Криуши, а также в населённые пункты Сенгилеевского района. Действует один внутригородской автобусный маршрут 100, обслуживаемый АО «Сенгилеевское АТП». В настоящее время, у Речного вокзала восстановлено летнее сообщение туристических речных судов',
                       'POINT(48.7988442 53.9664650)',
                       'ул. Луначарского, д.1',
                       4
                   );
        END IF;
    END $$;

SELECT setval('landmark_landmark_id_seq', (SELECT COALESCE(MAX(landmark_id), 6) FROM Landmark));