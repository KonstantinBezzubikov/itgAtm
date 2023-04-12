create or replace function get_acq_fee_amount2(
   	in var1 text,
	in var2 text,
	in var3 text,
	in var4 text,
	in var5 int,
	in var6 int,
	in var7 int,
	in var8 text,
	in var9 int,
   out res_msg int
)
as $$
begin
	res_msg = 20;
end;
$$ language plpgsql;



create or replace function get_acq_fee_amount(
   	in var1 text,
	in var2 text,
	in var3 text,
	in var4 int,
	in var5 int,
	in var6 int,
	in var7 text,
	in var8 int,
   out res_msg int
)
as $$
begin
	res_msg = 9;
end;
$$ language plpgsql;



create or replace function get_iss_fee_amount(
   	in var1 text,
	in var2 text,
	in var3 text,
	in var4 text,
	in var5 int,
	in var6 int,
	in var7 int,
	in var8 int,
	in var9 int,
	in var10 int,
   out res_msg1 int,
   out res_msg2 int
)
as $$
begin
	res_msg1 := 11;
	res_msg2 := 0;
end;
$$ language plpgsql;
