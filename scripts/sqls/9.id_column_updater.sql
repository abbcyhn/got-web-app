DO
$do$
DECLARE
   arr text[][] := array[
      ['characters','id'],
      ['actors','id'],
      ['actions','id'],
      ['allies','id'],
      ['houses','id'],
      ['relationships','id']
   ];
   seq_name text;
   seq_val bigint;
BEGIN
   FOR i IN array_lower(arr, 1) .. array_upper(arr, 1)
   LOOP
      EXECUTE format('SELECT pg_get_serial_sequence(%L, %L)', arr[i][1], arr[i][2]) INTO seq_name;
      EXECUTE format('SELECT MAX(%I) FROM %I', arr[i][2], arr[i][1]) INTO seq_val;
      IF seq_val IS NOT NULL THEN
         EXECUTE format('SELECT setval(%L, %L)', seq_name, seq_val + 1);
      END IF;
   END LOOP;
END
$do$;
