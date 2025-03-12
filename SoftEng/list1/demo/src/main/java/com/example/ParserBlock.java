package com.example;

import java.util.ArrayList;

public class ParserBlock 
{
    private ArrayList<Parser> list;

    public ParserBlock()
    {
        list = new ArrayList<>();

        // Integer parser
        list.add(new Parser()
        {
            private Integer result;

            @Override
            public boolean parse(String input)
            {
                try 
                {
                    result = Integer.parseInt(input);
                    return true;
                } 
                catch (NumberFormatException e) 
                {
                    return false;
                }
            }

            @Override
            public String getResult()
            {
                return result != null ? result.toString() : "";
            }

            @Override
            public String getType()
            {
                return "Integer";
            }
        });

        // Double parser
        list.add(new Parser()
        {
            private Double result;

            @Override
            public boolean parse(String input)
            {
                try 
                {
                    result = Double.parseDouble(input);
                    return true;
                } 
                catch (NumberFormatException e) 
                {
                    return false;
                }
            }

            @Override
            public String getResult()
            {
                return result != null ? result.toString() : "";
            }

            @Override
            public String getType()
            {
                return "Double";
            }
        });

        // Float parser
        list.add(new Parser()
        {
            private Float result;

            @Override
            public boolean parse(String input)
            {
                try 
                {
                    result = Float.parseFloat(input);
                    return true;
                } 
                catch (NumberFormatException e) 
                {
                    return false;
                }
            }

            @Override
            public String getResult()
            {
                return result != null ? result.toString() : "";
            }

            @Override
            public String getType()
            {
                return "Float";
            }
        });

        // Long parser
        list.add(new Parser()
        {
            private Long result;

            @Override
            public boolean parse(String input)
            {
                try 
                {
                    result = Long.parseLong(input);
                    return true;
                } 
                catch (NumberFormatException e) 
                {
                    return false;
                }
            }

            @Override
            public String getResult()
            {
                return result != null ? result.toString() : "";
            }

            @Override
            public String getType()
            {
                return "Long";
            }
        });

        // Char parser (single character)
        list.add(new Parser()
        {
            private Character result;

            @Override
            public boolean parse(String input)
            {
                if (input.length() == 1) 
                {
                    result = input.charAt(0);
                    return true;
                }
                return false;
            }

            @Override
            public String getResult()
            {
                return result != null ? result.toString() : "";
            }

            @Override
            public String getType()
            {
                return "Character";
            }
        });

        // Unsigned int parser (0 to 4294967295 range)
        list.add(new Parser()
        {
            private Long result;

            @Override
            public boolean parse(String input)
            {
                try 
                {
                    long value = Long.parseLong(input);
                    if (value >= 0 && value <= 4294967295L) 
                    {
                        result = value;
                        return true;
                    }
                    return false;
                } 
                catch (NumberFormatException e) 
                {
                    return false;
                }
            }

            @Override
            public String getResult()
            {
                return result != null ? result.toString() : "";
            }

            @Override
            public String getType()
            {
                return "Unsigned Int (0-4294967295)";
            }
        });

        // Boolean parser
        list.add(new Parser()
        {
            private Boolean result;

            @Override
            public boolean parse(String input)
            {
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) 
                {
                    result = Boolean.parseBoolean(input);
                    return true;
                }
                return false;
            }

            @Override
            public String getResult()
            {
                return result != null ? result.toString() : "";
            }

            @Override
            public String getType()
            {
                return "Boolean";
            }
        });

        // String parser (always succeeds)
        list.add(new Parser()
        {
            private String result;

            @Override
            public boolean parse(String input)
            {
                result = input;
                return true; // Always succeeds for String
            }

            @Override
            public String getResult()
            {
                return result != null ? result : "";
            }

            @Override
            public String getType()
            {
                return "String";
            }
        });
    }

    public ArrayList<Parser> getParsers()
    {
        return list;
    }
}
